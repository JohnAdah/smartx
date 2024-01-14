package com.booking.smartx.dto.responseDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationResponseDto {
    private String businessName;
    private String email;
    private String phoneNumber;
    private String postCode;
    private String address;
    private String businessType;
    private int numberOfEmployees;
    private String role;
}
