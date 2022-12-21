package com.daniel.droneServices.response;

import com.daniel.droneServices.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicationResponse {
    private String message;
    private String serialNumber;
    private LocalDateTime timestamp;
    Medication medication;
}
