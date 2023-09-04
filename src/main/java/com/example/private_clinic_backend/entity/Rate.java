package com.example.private_clinic_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Rate {
    @JsonProperty("currency")

    private String currency;
    @JsonProperty("code")

    private String code;
    @JsonProperty("mid")

    private double mid;
    @Id
    private Long rateId;

}
