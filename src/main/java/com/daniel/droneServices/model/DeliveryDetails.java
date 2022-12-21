package com.daniel.droneServices.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Builder
public class DeliveryDetails extends BaseClass{

    @NotNull
    private String serialNumber;
    @NotNull
    private String source;
    @NotNull
    private String destination;

    private String medicationCode;
}
