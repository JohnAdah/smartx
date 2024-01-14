package com.booking.smartx.services.impl;

import com.booking.smartx.dao.UserRepository;
import com.booking.smartx.dao.TempInMemoryDb;
import com.booking.smartx.dto.requestDto.RegisterMerchantDto;
import com.booking.smartx.dto.responseDto.RegistrationResponseDto;
import com.booking.smartx.entities.User;
import com.booking.smartx.mapper.RegistrationMapper;
import com.booking.smartx.services.EmailService;
import com.booking.smartx.services.RegistrationService;
import com.booking.smartx.utils.Constants;
import com.booking.smartx.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final UserRepository merchantRepository;

    private final PasswordEncoder passwordEncoder;
    private final TempInMemoryDb tempInMemoryDb;
    private final EmailService emailService;

    private final EmailServiceImpl emailServiceImpl;

    @Value("${otp.notification.template}")
    private String otpTemplate;

    @Value("${successful.registration.notification.template}")
    private String successfulRegistrationTemplate;
    private HashMap<String, String> data;


    public RegistrationServiceImpl(UserRepository merchantRepository, PasswordEncoder passwordEncoder, TempInMemoryDb tempInMemoryDb, EmailService emailService, EmailServiceImpl emailServiceImpl, EmailServiceImpl emailServiceImpl1) {
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
        this.tempInMemoryDb = tempInMemoryDb;
        this.emailService = emailService;
        this.emailServiceImpl = emailServiceImpl1;
    }

    @Override
    public ResponseEntity<?> initiateRegistration(RegisterMerchantDto registerObj) {
        User checkExistingUser = merchantRepository.findByEmail(registerObj.getEmail());
        if(!Objects.isNull(checkExistingUser)){
            return new ResponseEntity<>(Constants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        registerObj.setToken(Utils.emailVerificationToken());
        tempInMemoryDb.saveData(registerObj.getEmail(),registerObj);
        //implementation to send email to user with verification token
        //TODO: set token validation period and include field in registerMerchantDto
        sendToken(registerObj);
        return new ResponseEntity<>("token successfully sent",HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> save(String email, String token) {
        // Confirm that User does not exist
        User checkExistingUser = merchantRepository.findByEmail(email);
        if(!Objects.isNull(checkExistingUser)){
            return new ResponseEntity<>(Constants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        //fetch user details from temp DB
        RegisterMerchantDto tempRegData = (RegisterMerchantDto) tempInMemoryDb.getData(email);
        //validate token for email verification
        if(!(tempRegData.getToken() == Integer.parseInt(token))){
            return new ResponseEntity<>(Constants.INVALID_TOKEN_PROVIDED, HttpStatus.FORBIDDEN);
        }
        //TODO: Validate token expiration period
        //Save user into DB
        User registration = RegistrationMapper.merchantRegistrationMapper(tempRegData);
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));

        RegistrationResponseDto response = RegistrationMapper
                .merchantResponseMapper(merchantRepository.save(registration));

        data = new HashMap<>();
        data.put("name", String.valueOf(registration.getBusinessName()));
        String emailBody = emailServiceImpl.prepareEmailTemplate(successfulRegistrationTemplate, data);
        emailService.sendEmail(registration.getEmail(), emailBody,
                Constants.REGISTRATION_SUCCESSFUL);

        tempInMemoryDb.deleteData(email);
        log.info("New User Registration Successful.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<?> findByEmail(String email) {
        User checkExistingUser = merchantRepository.findByEmail(email);
        if(Objects.isNull(checkExistingUser)){
            return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        RegistrationResponseDto response = RegistrationMapper.merchantResponseMapper(checkExistingUser);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> resendToken(String email) {
        RegisterMerchantDto tempRegData = (RegisterMerchantDto) tempInMemoryDb.getData(email);
        if(Objects.isNull(tempRegData)){
            return new ResponseEntity<>(Constants.USER_NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        tempRegData.setToken(Utils.emailVerificationToken());
        sendToken(tempRegData);
        return new ResponseEntity<>("token successfully sent",HttpStatus.OK);
    }

    private void sendToken (RegisterMerchantDto tempRegData){
        data = new HashMap<>();
        data.put("token", String.valueOf(tempRegData.getToken()));
        String emailBody = emailServiceImpl.prepareEmailTemplate(otpTemplate, data);
        emailService.sendEmail(tempRegData.getEmail(), emailBody,
                Constants.EMAIL_VERIFICATION_SUBJECT);
        log.info("Token sent to user email: {}",tempRegData.getEmail());
    }


}
