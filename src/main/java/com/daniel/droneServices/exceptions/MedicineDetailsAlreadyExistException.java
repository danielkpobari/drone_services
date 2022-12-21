package com.daniel.droneServices.exceptions;

public class MedicineDetailsAlreadyExistException extends RuntimeException{
    public MedicineDetailsAlreadyExistException(String message){
        super (message);
    }
}
