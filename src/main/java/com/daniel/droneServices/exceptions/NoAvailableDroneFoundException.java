package com.daniel.droneServices.exceptions;

public class NoAvailableDroneFoundException extends RuntimeException{
    public NoAvailableDroneFoundException(String message){
        super(message);
    }
}
