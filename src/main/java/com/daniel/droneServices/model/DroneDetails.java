package com.daniel.droneServices.model;

import com.daniel.droneServices.enums.DroneModel;
import com.daniel.droneServices.enums.DroneState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "drone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DroneDetails extends BaseClass implements Serializable {
    @Column(unique = true)
    @Max(value = 100 , message = "serialNumber should not be more than 100 characters")
    private String serialNumber;
    @DecimalMax(value = "500", message =" Drone cannot carry more than {value} grams")
    private double weight;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Enumerated(EnumType.STRING)
    private DroneState state;
    @Pattern(regexp = "^(100|[1-9]?\\d)$",message = "Battery can not be more than 100%")
    private BigDecimal batteryCapacity;
    @OneToMany(mappedBy = "drone")
    private List<Medication> medication;

    public DroneDetails(String serialNumber, double weight, DroneModel model, DroneState state, BigDecimal batteryCapacity){
        this.serialNumber = serialNumber;
        this.weight = weight;
        this.model = model;
        this.state = state;
        this.batteryCapacity = batteryCapacity;
    }

}
