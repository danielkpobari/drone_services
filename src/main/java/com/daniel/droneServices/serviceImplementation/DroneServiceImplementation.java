package com.daniel.droneServices.serviceImplementation;

import com.daniel.droneServices.dto.DroneDTO;
import com.daniel.droneServices.dto.DroneDeliveryDTO;
import com.daniel.droneServices.enums.DroneState;
import com.daniel.droneServices.exceptions.*;
import com.daniel.droneServices.model.DeliveryDetails;
import com.daniel.droneServices.model.DroneDetails;
import com.daniel.droneServices.model.Medication;
import com.daniel.droneServices.repository.DeliveryRepository;
import com.daniel.droneServices.repository.DroneRepository;
import com.daniel.droneServices.repository.MedicationRepository;
import com.daniel.droneServices.response.ApiResponse;
import com.daniel.droneServices.service.DroneService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class DroneServiceImplementation implements DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public ApiResponse<DroneDTO> registerDrone(DroneDTO droneDto) {
        // A drone should be registered just once ;
        String serialNumber = droneDto.getSerialNumber();
        Optional<DroneDetails> registeredDrone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (registeredDrone.isEmpty()) {
            DroneDetails droneDetails = DroneDetails.builder()
                    .model(droneDto.getModel())
                    .weight(droneDto.getWeight())
                    .batteryCapacity(droneDto.getBatteryCapacity())
                    .serialNumber(droneDto.getSerialNumber())
                    .state(droneDto.getState())
                    .build();
            droneRepository.save(droneDetails);
            return new ApiResponse<>("Drone registered successfully", droneDto);
        } else {
            throw new DroneAlreadyRegisteredException("Drone with serialNumber: " + droneDto.getSerialNumber() + " already registered");
        }
    }

    @Override
    public ApiResponse<?> getAvailableDrones() {
        //get all drones that are not working yet
        List<DroneDetails> drones = droneRepository.findAllByState(DroneState.IDLE);
        List<DroneDetails> dto = new ArrayList<>();

        for(DroneDetails drone : drones){
            if (drone.getBatteryCapacity().compareTo(new BigDecimal(0.25)) > 0) {
                dto.add(drone);
            } else {
                throw new BatteryAndDroneStateException("No available drones");
            }}
        return new ApiResponse<>("status", dto);
    }

    @Override
    public ApiResponse<?> getAllDrones() {
        List<DroneDetails> drones = droneRepository.findAll();
        return new ApiResponse<>("success", drones);
    }

    @Override
    public DroneDetails getADroneState() {
        // this gets a drone that is not working yet
        DroneDetails drone = droneRepository.findByState(DroneState.IDLE);
        return drone;
    }

    @Override

    public ApiResponse<DroneDetails> getADrone(String serialNo) {
        DroneDetails drone = droneRepository.findDroneBySerialNumber(serialNo).orElseThrow(
                () -> new NoAvailableDroneFoundException("Not found ")
        );
        return new ApiResponse<>("success", drone);
    }

    public DroneDetails getBySerialNumberAndState(String serialNo) {
        DroneDetails loadingDrone = droneRepository.findDroneBySerialNumber(serialNo).orElseThrow(
                () -> new NoAvailableDroneFoundException("Not found "));
        if (loadingDrone.getState().equals(DroneState.IDLE) && loadingDrone.getBatteryCapacity().compareTo(new BigDecimal(0.25)) > 0) {
            return loadingDrone;
        } else {
            throw new BatteryAndDroneStateException("the drone state is " + loadingDrone.getState());
        }
    }
    @Override
    public ApiResponse<DroneDetails> loadDrone(String droneSerialNo, String medCode) {
        double medWeight = 0.0;
        DroneDetails loadingDrone = getBySerialNumberAndState(droneSerialNo);
        Medication medicine = medicationRepository.findByCode(medCode).orElseThrow(
                () -> new MedicineDetailsAlreadyExistException("Not found")
        );
        loadingDrone.getMedication().add(medicine);

        for (Medication med : loadingDrone.getMedication()) {
            medWeight += med.getWeight();
        }

        loadingDrone.setState(DroneState.LOADING);
        if ((medWeight + loadingDrone.getWeight()) <= 500) {
            loadingDrone.setState(DroneState.LOADED);
            droneRepository.save(loadingDrone);
            return new ApiResponse<>("loaded successfully", loadingDrone);

        } else {
            throw new DroneLimitException("limit exceeded");

        }
    }

    @Override
    public ApiResponse<String> deliverMedication(DroneDeliveryDTO droneDeliveryDto) {
        DroneDetails drone = droneRepository.findDroneBySerialNumber(droneDeliveryDto.getSerialNumber()).orElseThrow(
                () -> new NoAvailableDroneFoundException("Not found")
        );

        if (drone.getState().equals(DroneState.LOADED)) {
            drone.setState(DroneState.DELIVERING);
            droneRepository.save(drone);
            DeliveryDetails deliveryDetails = DeliveryDetails.builder()
                    .serialNumber(droneDeliveryDto.getSerialNumber())
                    .source(droneDeliveryDto.getSource())
                    .destination(droneDeliveryDto.getDestination())
                    .medicationCode(droneDeliveryDto.getMedicineCode())
                    .build();
            deliveryRepository.save(deliveryDetails);
//            drone.setState(DroneState.DELIVERED);
            String message = "Drone with serial number " + droneDeliveryDto.getSerialNumber() + " is set to be delivered";
            return new ApiResponse<>("delivering in progress", message);
        } else {
            throw new NoAvailableDroneFoundException("Drone with serialNumber: " + droneDeliveryDto.getSerialNumber() + " Not loaded");
        }
    }

    @Override
    public ApiResponse<?> getLoadedMedication(String serialNo){
        DroneDetails drone = droneRepository.findBySerialNumberAndState(serialNo, DroneState.LOADED);
        return new ApiResponse<>("success", drone.getMedication());
    }
    @Override
    public void periodicCheckForBatteryHealth(List<DroneDetails> drones) {
        Logger logger = LoggerFactory.getLogger(DroneServiceImplementation.class);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                drones.forEach(drone ->  logger
                        .info("Battery Level for --- " + drone.getSerialNumber() + " is " + drone.getBatteryCapacity()));
            }
        } ,2000, 200000);

    }

    @Override
    public ApiResponse<?> checkBatteryLevel(String serialNumber) {
        DroneDetails drone = droneRepository.findDroneBySerialNumber(serialNumber).orElseThrow(
                () -> new NoAvailableDroneFoundException("Not found")
        );
        BigDecimal batteryLevel = drone.getBatteryCapacity();
        return  new ApiResponse<>("Drone with serialNumber " + serialNumber + " has a battery level of:" , batteryLevel);
    }
}
