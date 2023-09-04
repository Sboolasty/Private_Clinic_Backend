package com.example.private_clinic_backend;

import com.example.private_clinic_backend.dto.DoctorDto;
import com.example.private_clinic_backend.dto.PatientDto;
import com.example.private_clinic_backend.dto.RegistrationDto;
import com.example.private_clinic_backend.entity.*;
import com.example.private_clinic_backend.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PrivateClinicBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrivateClinicBackendApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){ return new RestTemplate(); }
//	private UserService userService;
//	private DoctorService doctorService;
//	private PatientService patientService;
//	private AppointmentService appointmentService;
//	private AvailabilityDateService availabilityDateService;
//	public PrivateClinicBackendApplication(UserService userService, DoctorService doctorService, PatientService patientService, AppointmentService appointmentService, AvailabilityDateService availabilityDateService) {
//		this.userService = userService;
//		this.doctorService = doctorService;
//		this.patientService = patientService;
//		this.appointmentService = appointmentService;
//		this.availabilityDateService = availabilityDateService;
//	}
//
//	public PrivateClinicBackendApplication(){}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void fill() {
//
////		            User userPatient = new User("01010", "pacjent@o2.pl","pacjent", "Mikolaj", "Kiernikowski", "555", "addr", new ArrayList(), UserRole.PATIENT);
////            User userDoctor = new User("010102", "doktor@o2.pl","doktor", "Dr Piotr", "Napierala", "555", "addr", new ArrayList<>(), UserRole.DOCTOR);
//		UserService userService1 = new UserService();
//		this.userService = userService1;
//		PatientDto patientDto = new PatientDto("12345", 100, 1.8);
//
//		RegistrationDto registrationDto = new RegistrationDto("12345", "pacjent@o2.pl","pacjent", "Mikolaj", "Kiernikowski", "555", "addr", null, patientDto );
//
//		this.userService.registration(registrationDto);
//
//
//	}



}
