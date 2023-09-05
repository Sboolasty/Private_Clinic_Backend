package com.example.private_clinic_backend.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.private_clinic_backend.dto.AppointmentDto;
import com.example.private_clinic_backend.dto.ReserveAppointmentDto;
import com.example.private_clinic_backend.dto.ResultAppointmentDto;
import com.example.private_clinic_backend.entity.*;
import com.example.private_clinic_backend.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Setter
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvailabilityDateRepository availabilityDateRepository;


    @Autowired
    private UserService userService;

    public ResultAppointmentDto reserveAppointment(ReserveAppointmentDto reserveAppointmentDto) {
        int result=0;
        ResultAppointmentDto resultAppointmentDto = new ResultAppointmentDto();
        Object [] users = userService.findUser(reserveAppointmentDto.getPatientDto(), reserveAppointmentDto.getDoctorDto());
        Patient patient = (Patient) users[0];
        Doctor doctor = (Doctor) users[1];
        if (patient == null)
        {
            resultAppointmentDto.setPatientIdNumber(null);
            result=1;
        }
        if (doctor == null)
        {
            resultAppointmentDto.setDoctorLicenseNumber(null);
            result++;
        }
        if (result > 0) return resultAppointmentDto;

        resultAppointmentDto.setPatientIdNumber(patient.getUser().getIdNumber());
        resultAppointmentDto.setDoctorLicenseNumber(doctor.getLicenseNumber());

        Appointment appointment = findFreeTerm(users, reserveAppointmentDto.getDate(), reserveAppointmentDto.getDuration());

        appointment.setDescription(reserveAppointmentDto.getDescription());
        resultAppointmentDto.setDescription(reserveAppointmentDto.getDescription());

        if (appointment.getAppointmentDate() == null){
            resultAppointmentDto.setDate(null);
            return resultAppointmentDto;
        }
        resultAppointmentDto.setDate(reserveAppointmentDto.getDate());

        patient.getUser().getAppointmentList().add(appointment);
        doctor.getUser().getAppointmentList().add(appointment);

        appointmentRepository.save(appointment);
        userRepository.save(patient.getUser());
        userRepository.save(doctor.getUser());
        return resultAppointmentDto;
    }

    public Appointment findFreeTerm (Object [] users, LocalDateTime date, int durationMinutes)
    {
        Patient patient = (Patient) users[0];
        Doctor doctor = (Doctor)users[1];

        List<AvailabilityDate> availabilityDates = doctor.getAvailabilityDates();

        Optional<AvailabilityDate> adOptional = availabilityDates.stream().filter(a -> a.getDate().isEqual(date)).findFirst();

        if (adOptional.isPresent()){
            AvailabilityDate ad = adOptional.get();
            if (ad.isFree() && ad.getDurationMinutes() >= durationMinutes){
                ad.setFree(false);
                availabilityDateRepository.save(ad);

                return new Appointment(ad, AppointmentStatus.SCHEDULED, null, patient);
            }
        }
        return new Appointment(null, AppointmentStatus.SCHEDULED, null, patient);
    }

    public Optional<AvailabilityDate> checkFreeTerm (Doctor doctor, LocalDateTime date, int durationMinutes){
        List<AvailabilityDate> availabilityDates = doctor.getAvailabilityDates();
        Optional<AvailabilityDate> adOptional = availabilityDates.stream().filter(a -> a.getDate().isEqual(date)).findFirst();
        return adOptional;

    }

    public ResultAppointmentDto updateAppointment(Long appointmentId, ReserveAppointmentDto reserveAppointmentDto) {
        ResultAppointmentDto resultAppointmentDto = new ResultAppointmentDto();

        Object [] users = userService.findUser(reserveAppointmentDto.getPatientDto(), reserveAppointmentDto.getDoctorDto());
        Patient patient = (Patient) users[0];
        Doctor doctor = (Doctor) users[1];



        int result = 0;
        if (patient == null)
        {
            resultAppointmentDto.setPatientIdNumber(null);
            result=1;
        }
        if (doctor == null)
        {
            resultAppointmentDto.setDoctorLicenseNumber(null);
            result++;
        }
        if (result > 0) return resultAppointmentDto;

        Optional<AvailabilityDate> availabilityDateOptional = checkFreeTerm(doctor, reserveAppointmentDto.getDate(), 30);

        if (availabilityDateOptional.isPresent()){
            resultAppointmentDto.setPatientIdNumber(patient.getUser().getIdNumber());
            resultAppointmentDto.setDoctorLicenseNumber(doctor.getLicenseNumber());

            Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
            Appointment appointment = null;
            if (appointmentOpt.isPresent())
                appointment = appointmentOpt.get();
            else
                return null;
            // set previousDate status free

            AvailabilityDate prevAvailabilityDate = appointment.getAppointmentDate();
            prevAvailabilityDate.setFree(true);
            availabilityDateRepository.save(prevAvailabilityDate);


            // set new date
            appointment.setAppointmentDate(availabilityDateOptional.get());
            availabilityDateOptional.get().setFree(false);
            availabilityDateRepository.save(availabilityDateOptional.get());


            appointment.setDescription(reserveAppointmentDto.getDescription());
            resultAppointmentDto.setDescription(reserveAppointmentDto.getDescription());

            resultAppointmentDto.setDate(reserveAppointmentDto.getDate());

            appointmentRepository.save(appointment);

            return resultAppointmentDto;
        } else
            return null;




    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Boolean deleteAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        Appointment appointment = null;
        if (appointmentOpt.isPresent()) {
            appointment = appointmentOpt.get();
            User patient = appointment.getPatient().getUser();
            User doctor = appointment.getAppointmentDate().getDoctor().getUser();

            Appointment appointment1 = patient.getAppointmentList().stream()
                    .filter(a -> a.getAppointmentId().equals(appointmentId))
                    .findFirst().orElse(null);

            Appointment appointment2 = doctor.getAppointmentList().stream()
                    .filter(a -> a.getAppointmentId().equals(appointmentId))
                    .findFirst().orElse(null);

            if (appointment1 == null || appointment2 == null){
                return null;
            }

            AvailabilityDate availabilityDate = appointment.getAppointmentDate();
            availabilityDate.setFree(true);
            availabilityDateRepository.save(availabilityDate);

            patient.getAppointmentList().remove(appointment1);

            doctor.getAppointmentList().remove(appointment2);

            userRepository.save(patient);
            userRepository.save(doctor);


            appointmentRepository.delete(appointment);

            return true;
        }
        else
            return false;


    }
}
