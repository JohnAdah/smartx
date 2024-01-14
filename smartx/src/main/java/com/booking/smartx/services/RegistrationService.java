package com.booking.smartx.services;

import com.booking.smartx.dto.requestDto.RegisterMerchantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


public interface RegistrationService {

    ResponseEntity<?> save (String email, String token);
    ResponseEntity<?> findByEmail(String email);

    ResponseEntity<?> initiateRegistration(RegisterMerchantDto obj);

    ResponseEntity<?> resendToken(String email);
}
