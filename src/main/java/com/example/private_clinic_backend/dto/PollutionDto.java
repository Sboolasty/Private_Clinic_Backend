package com.example.private_clinic_backend.dto;

import com.example.private_clinic_backend.entity.Value;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PollutionDto {
    @JsonProperty("key")
    private String key;
    @JsonProperty("values")
    private List<Value> values;
}
