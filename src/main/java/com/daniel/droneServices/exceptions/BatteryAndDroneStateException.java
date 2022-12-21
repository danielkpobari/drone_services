package com.daniel.droneServices.exceptions;

public class BatteryAndDroneStateException extends RuntimeException{
    public BatteryAndDroneStateException(String message){
        super(message);
    }
}
