package com.daniel.droneServices;

import com.daniel.droneServices.enums.DroneModel;
import com.daniel.droneServices.enums.DroneState;
import com.daniel.droneServices.model.DroneDetails;
import com.daniel.droneServices.model.Medication;
import com.daniel.droneServices.repository.DroneRepository;
import com.daniel.droneServices.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class DroneServicesApplication implements CommandLineRunner {
	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;
	private static final Logger logger = LoggerFactory.getLogger(DroneServicesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DroneServicesApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {


		droneRepository.save(new DroneDetails("DJI_MAVIC03_AIR_2", 120.0, DroneModel.LIGHTWEIGHT, DroneState.IDLE, new BigDecimal(100)));
		droneRepository.save(new DroneDetails("DJI_MAVIC05_AIR_3", 200.8, DroneModel.CRUISERWEIGHT, DroneState.IDLE, new BigDecimal(34)));
		droneRepository.save(new DroneDetails("DJI_MAVIC07_AIR_4", 423.0, DroneModel.LIGHTWEIGHT, DroneState.LOADED, new BigDecimal(40)));
		droneRepository.save(new DroneDetails("DJI_MAVIC02_AIR_5", 455.3, DroneModel.HEAVYWEIGHT, DroneState.DELIVERING, new BigDecimal(59)));
		droneRepository.save(new DroneDetails("DJI_MAVIC01_AIR_1", 130.5, DroneModel.LIGHTWEIGHT, DroneState.IDLE, new BigDecimal(75)));
		droneRepository.save(new DroneDetails("ASX_MAVIC05_AIR_7", 204.0, DroneModel.MIDDLEWEIGHT, DroneState.IDLE, new BigDecimal(13)));
		droneRepository.save(new DroneDetails("0DJ_MAVIC34_AIR_2", 411.5, DroneModel.LIGHTWEIGHT, DroneState.LOADED, new BigDecimal(45)));
		droneRepository.save(new DroneDetails("DJS_MAVIC52_AIR_8", 405.0, DroneModel.CRUISERWEIGHT, DroneState.LOADED, new BigDecimal(80)));
		droneRepository.save(new DroneDetails("DSF_MAVIC08_AIR_2", 50.07, DroneModel.CRUISERWEIGHT, DroneState.IDLE, new BigDecimal(55)));
		droneRepository.save(new DroneDetails("AMP_MAVIC29_AIR_2", 34.03, DroneModel.HEAVYWEIGHT, DroneState.IDLE, new BigDecimal(35)));

		logger.info("Drone Details saved");


		medicationRepository.save(new Medication("Paracetamol", 50.0, "PC003", "image01.jpeg"));
		medicationRepository.save(new Medication("Vitamin D", 206.0, "VT0D3", "image02.jpeg"));
		medicationRepository.save(new Medication("Ibuprofen", 120.0, "IB35OB", "image03.jpeg"));
		medicationRepository.save(new Medication("Levothyroxine", 134.0, "LTR045", "image04.jpeg"));
		medicationRepository.save(new Medication("Amlodipine", 34.05, "AL0341", "image05.jpeg"));
		medicationRepository.save(new Medication("Prednisone", 34.70, "PR349", "image06.jpeg"));

		logger.info("Medication Details saved");
	}
}
