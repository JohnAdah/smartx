package com.booking.smartx.controllers;

import com.booking.smartx.dto.requestDto.RegisterMerchantDto;
import com.booking.smartx.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    //initiate registration by collecting data and saving to temporary DB
    @PostMapping("/initiate")
    public ResponseEntity<?> registerMerchant(@RequestBody RegisterMerchantDto request){
        return ResponseEntity.ok(registrationService.initiateRegistration(request));
    }

    //Endpoint to validate token sent to user email and save the user to the DB
    @PostMapping(value = "/complete-registration")
    public ResponseEntity<?> completeRegistration(@RequestHeader String email, @RequestHeader String token){
        return ResponseEntity.ok(registrationService.save(email,token));
    }

    /*
    Endpoint to confirm user email exist
     */
    @GetMapping(value = "/verify-email")
    public ResponseEntity<?> confirmUserEmailExists(@RequestParam String email){
        return registrationService.findByEmail(email);
    }

    /*
    Endpoint to resend token after expiration period elapses
     */
    @GetMapping(value = "/resend-token")
    public ResponseEntity<?> resendToken(@RequestParam String email){
        return registrationService.resendToken(email);
    }
}
