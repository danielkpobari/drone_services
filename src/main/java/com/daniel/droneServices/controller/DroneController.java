package com.daniel.droneServices.controller;

import com.daniel.droneServices.dto.DroneDTO;
import com.daniel.droneServices.dto.DroneDeliveryDTO;
import com.daniel.droneServices.dto.MedicationDTO;
import com.daniel.droneServices.service.DroneService;
import com.daniel.droneServices.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DroneController {
    private final DroneService droneService;
    private final MedicationService medicationService;
    @Operation(summary = "Registering a drone")
    @PostMapping("/register-drone")
    public ResponseEntity<?> registerDrone(@RequestBody DroneDTO dto){
        log.info("success");
        return new ResponseEntity<>(droneService.registerDrone(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get the list of all Drones")
    @GetMapping("/all-drones")
    public ResponseEntity<?> getAllDrones(){
        return new ResponseEntity<>(droneService.getAllDrones(), OK);
    }

    @Operation(summary = "Save a medication")
    @PostMapping("/save-medicine")
    public ResponseEntity<?> saveMedicineDetails(@RequestBody MedicationDTO medicationDto) {
        log.info("success");
        return new ResponseEntity<>(medicationService.medicationDetails(medicationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "view the list of all medications")
    @GetMapping("/all-medication")
    public ResponseEntity<?> getAllMedication(){
        return new ResponseEntity<>(medicationService.listOfMedications(), OK);
    }

    @Operation(summary = "Getting the list of all Idle drones")
    @GetMapping("/available-drone")
    public ResponseEntity<?> AvailableDrone(){
        log.info("available drones retrieve successfully");
        return new ResponseEntity<>(droneService.getAvailableDrones(), OK);
    }

    @Operation(summary = "Getting a single available drone")
    @GetMapping("/get_drone/{drone_no}")
    public ResponseEntity<?> findAnIdleDrone(@PathVariable(value="drone_no") String serialNumber){
        log.info("drone found successfully");
        return new ResponseEntity<>(droneService.getADrone(serialNumber), OK);
    }

    @Operation(summary = "Loading a drone with a medication")
    @GetMapping("/load-drone/{drone_no}/{med-code}")
    public ResponseEntity<?> loadingDrone(@PathVariable(value="drone_no") String serialNumber, @PathVariable(value="med-code")String code){
        log.info("drone loaded successfully");
        return new ResponseEntity<>(droneService.loadDrone(serialNumber, code), HttpStatus.OK);
    }

    @Operation(summary = "Delivering a drone with a medication")
    @PostMapping("/droneDelivery")
    public ResponseEntity<?> DeliverMedication( @RequestBody DroneDeliveryDTO dto){
        log.info("delivered");
        return new ResponseEntity<>(droneService.deliverMedication(dto), HttpStatus.OK);
    }
    @Operation(summary = "Getting the list of loaded medications on a drone")
    @GetMapping("/loaded-medication/{drone_no}")
    public ResponseEntity<?> getLoadedMedication(@PathVariable(value="drone_no") String serialNo){
        log.info("loaded medication retrieved successfully");
        return new ResponseEntity<>(droneService.getLoadedMedication(serialNo), OK);
    }

    @Operation(summary = "Viewing the battery level of a drone")
    @GetMapping("/battery-level/{drone_no}")
    public ResponseEntity<?> batteryLevelOfADrone(@PathVariable(value="drone_no") String serialNo){
        log.info("successfully");
        return new ResponseEntity<>(droneService.checkBatteryLevel(serialNo), OK);
    }

}
