package com.daniel.droneServices.controller;

import com.daniel.droneServices.exceptions.*;
import com.daniel.droneServices.response.ExceptionResponse;
import com.daniel.droneServices.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final ResponseService<ExceptionResponse> responseService;

    @ExceptionHandler(DroneAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundHandler(DroneAlreadyRegisteredException exception){
        return responseService.response(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BatteryAndDroneStateException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundHandler(BatteryAndDroneStateException exception){
        return responseService.response(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicineDetailsAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundHandler(MedicineDetailsAlreadyExistException exception){
        return responseService.response(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoAvailableDroneFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundHandler(NoAvailableDroneFoundException exception){
        return responseService.response(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DroneLimitException.class)
    public ResponseEntity<ExceptionResponse> droneLimitHandler(DroneLimitException exception){
        return responseService.response(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
}
