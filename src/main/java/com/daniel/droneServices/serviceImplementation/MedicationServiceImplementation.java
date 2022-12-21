package com.daniel.droneServices.serviceImplementation;

import com.daniel.droneServices.dto.MedicationDTO;
import com.daniel.droneServices.exceptions.MedicineDetailsAlreadyExistException;
import com.daniel.droneServices.model.Medication;
import com.daniel.droneServices.repository.MedicationRepository;
import com.daniel.droneServices.response.ApiResponse;
import com.daniel.droneServices.service.MedicationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class MedicationServiceImplementation implements MedicationService {
    private final MedicationRepository medicationRepository;


    @Override
    public ApiResponse<MedicationDTO> medicationDetails(MedicationDTO medicationDto) {
        Optional<Medication> medicine = medicationRepository.findByCode(medicationDto.getCode());
        if (medicine.isEmpty()) {
            Medication medication = Medication.builder()
                    .name(medicationDto.getName())
                    .weight(medicationDto.getWeight())
                    .code(medicationDto.getCode())
                    .image(medicationDto.getImage())
                    .build();

            medicationRepository.save(medication);
            return new ApiResponse<>("Medication details entered successfully", medicationDto);

        } else
            throw new MedicineDetailsAlreadyExistException("Medication details already entered");

    }

    @Override
    public ApiResponse<?> listOfMedications() {
        List<Medication> medication = medicationRepository.findAll();
        return new ApiResponse<>("success", medication);
    }
}