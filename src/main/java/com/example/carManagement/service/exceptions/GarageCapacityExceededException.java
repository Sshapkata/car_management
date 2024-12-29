package com.example.carManagement.service.exceptions;

public class GarageCapacityExceededException extends RuntimeException {
    public GarageCapacityExceededException(String message) {
        super(message);
    }
}