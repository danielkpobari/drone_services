package com.daniel.droneServices.exceptions;

public class DroneAlreadyRegisteredException extends RuntimeException{
    public DroneAlreadyRegisteredException(String message){
        super(message);
    }

}
