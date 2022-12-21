package com.daniel.droneServices.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="medication")
public class Medication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]$",
            message = "name should only contain letters, numbers, underscore and hyphen")
    @NotNull
    private String name;
    @NotNull
    private double weight;
    @Pattern(regexp="[A-Z0-9_]")
    @Column(unique = true)
    @NotNull
    private String code;
    private String image;
    @ManyToOne
    @JoinColumn(name = "loaddrone_id" , referencedColumnName = "id")
    private DroneDetails drone;



    public Medication(String name, double weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }
}
