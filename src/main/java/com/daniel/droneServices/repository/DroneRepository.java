package com.daniel.droneServices.repository;

import com.daniel.droneServices.enums.DroneState;
import com.daniel.droneServices.model.DroneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<DroneDetails, Long> {
    Optional<DroneDetails> findDroneBySerialNumber(String serialNumber);
    List<DroneDetails> findAllByState(DroneState droneState);
    DroneDetails findByState(DroneState droneState);

    DroneDetails findBySerialNumberAndState(String serialNumber, DroneState states);

}
