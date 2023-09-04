package com.example.private_clinic_backend.dto;

import com.example.private_clinic_backend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultUserDto {
    private UserRole role;

    private String idNumber;
}
