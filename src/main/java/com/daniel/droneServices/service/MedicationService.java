package com.daniel.droneServices.service;

import com.daniel.droneServices.dto.MedicationDTO;
import com.daniel.droneServices.response.ApiResponse;

public interface MedicationService {
    ApiResponse<?> medicationDetails(MedicationDTO medicationDto);


    ApiResponse<?> listOfMedications();
}
