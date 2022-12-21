package com.daniel.droneServices.exceptions;

public class DroneLimitException extends RuntimeException{
    public DroneLimitException(String message){
        super(message);
    }
}
