package com.example.private_clinic_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.private_clinic_backend.dto.ScheduleAppointmentDto;
import com.example.private_clinic_backend.entity.*;
import com.example.private_clinic_backend.repository.DoctorRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    public List<Doctor> findAllDoctors() {return doctorRepository.findAll();}

    public List<ScheduleAppointmentDto> findDoctorsAppointments(String licenseNumber) {
        Doctor doctor = doctorRepository.findDoctorByLicenseNumber(licenseNumber);

        if (doctor == null){
            return null;
        }

        List<Appointment> doctorsAppointments = doctor.getUser().getAppointmentList();

        List<Appointment> scheduledAppointments = doctorsAppointments.stream().filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED).collect(Collectors.toList());
        return createAppointmentDtoList(scheduledAppointments);
    }

    private List<ScheduleAppointmentDto> createAppointmentDtoList(List<Appointment> doctorsAppointments) {
        List<ScheduleAppointmentDto> dtoList = new ArrayList<>();
        for (Appointment a: doctorsAppointments
        ) {
            dtoList.add(new ScheduleAppointmentDto(a.getAppointmentId(), a.getAppointmentDate().getDate(), a.getPatient().getUser().getFirstName(), a.getPatient().getUser().getLastName(), a.getDescription(), a.getAppointmentDate().getDoctor().getUser().getFirstName(), a.getAppointmentDate().getDoctor().getUser().getLastName()));
        }

        return dtoList;
    }

    public String getLicenseNumberByUserIdNumber(String userIdNumber) {
        Doctor doctor = doctorRepository.findDoctorByUserIdNumber(userIdNumber);

        return doctor.getLicenseNumber();
    }

    public Doctor getDoctorByUserIdNumber(String userIdNumber) {
        return  doctorRepository.findDoctorByUserIdNumber(userIdNumber);
    }
}
