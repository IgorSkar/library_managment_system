package com.stav.library_managment_system.Exception;


public class apiRequestException extends RuntimeException {


    public apiRequestException(String message) {
        super(message);
    }

    public apiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

