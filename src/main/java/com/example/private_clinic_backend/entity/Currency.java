package com.example.private_clinic_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @JsonProperty("table")
    private String table;
    @JsonProperty("rates")

    private List<Rate> rateList;
}
