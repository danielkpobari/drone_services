package com.daniel.droneServices.service;

import com.daniel.droneServices.dto.DroneDTO;
import com.daniel.droneServices.dto.DroneDeliveryDTO;
import com.daniel.droneServices.model.DroneDetails;
import com.daniel.droneServices.response.ApiResponse;

import java.util.List;

public interface DroneService {
    ApiResponse<DroneDTO> registerDrone(DroneDTO droneDto);

    ApiResponse<?> getAvailableDrones();

    // DroneDetails getADroneState();

    ApiResponse<?> getAllDrones();

    DroneDetails getADroneState();

    ApiResponse<DroneDetails> getADrone(String serialNo);

    ApiResponse<DroneDetails> loadDrone(String droneSerialNo, String medCode);


    ApiResponse<String> deliverMedication(DroneDeliveryDTO droneDeliveryDto);

    ApiResponse<?> getLoadedMedication(String serialNo);

    void periodicCheckForBatteryHealth(List<DroneDetails> drones);

    ApiResponse<?> checkBatteryLevel(String serialNumber);
}
