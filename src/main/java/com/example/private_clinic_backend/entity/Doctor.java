package com.example.private_clinic_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDoctor;

    private String licenseNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "doctor", fetch= FetchType.EAGER)
    private List<AvailabilityDate> availabilityDates;

    public Doctor(String licenseNumber, User user, List<AvailabilityDate> availabilityDates) {
        this.licenseNumber = licenseNumber;
        this.user = user;
        this.availabilityDates = availabilityDates;
    }
}
