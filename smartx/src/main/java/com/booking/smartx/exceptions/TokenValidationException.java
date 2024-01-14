package com.booking.smartx.exceptions;

public class TokenValidationException extends RuntimeException{
    private String code;

    private String message;


    public TokenValidationException(String message) {
        this.message = message;
    }
}
