package com.daniel.droneServices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationDTO {
    @Pattern(regexp = "^[a-zA-Z0-9_-]$",
            message = "name should only contain letters, numbers, underscore and hyphen")
    private String name;
    private Double weight;

    @Pattern(regexp="[A-Z0-9_]")
    private String code;
    private String image;
}
