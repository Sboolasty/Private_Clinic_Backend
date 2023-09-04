package com.example.private_clinic_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String idNumber;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    private List<Appointment> appointmentList;

    private UserRole role;

    public User(String idNumber, String email, String password, String firstName, String lastName, String phoneNumber, String address, List<Appointment> appointmentList, UserRole role) {
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointmentList = new ArrayList<Appointment>();
        this.role = role;
    }

}

