package com.daniel.droneServices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneDeliveryDTO {
    private String serialNumber;
    private String medicineCode;
    private String source;
    private String destination;
}
