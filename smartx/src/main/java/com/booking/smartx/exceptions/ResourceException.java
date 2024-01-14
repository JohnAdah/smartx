package com.booking.smartx.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResourceException extends RuntimeException {
    private String code;

    private String message;


    public ResourceException(String message) {
        this.message = message;
    }
}
