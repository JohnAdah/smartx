package com.booking.smartx.services.impl;

import com.booking.smartx.dao.LoginRepository;
import com.booking.smartx.dao.TempInMemoryDb;
import com.booking.smartx.dao.UserRepository;
import com.booking.smartx.dto.requestDto.LoginRequestDto;
import com.booking.smartx.dto.requestDto.RegisterMerchantDto;
import com.booking.smartx.dto.requestDto.ResetPasswordDto;
import com.booking.smartx.dto.responseDto.LoginResponse;
import com.booking.smartx.entities.User;
import com.booking.smartx.mapper.RegistrationMapper;
import com.booking.smartx.mapper.UserMapper;
import com.booking.smartx.security.SecurityUtil;
import com.booking.smartx.services.LoginService;
import com.booking.smartx.utils.Constants;
import com.booking.smartx.utils.Utils;
import org.apache.commons.lang3.StringUtils;
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
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final UserRepository userRepository;

    private final LoginRepository loginRepository;

    private final UserServiceImpl userService;
    @Value("${reset.password.template}")
    private String resetPasswordTemplate;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil jwtUtil;
    private final RegistrationServiceImpl registrationService;
    private final TempInMemoryDb tempInMemoryDb;
    private HashMap<String, String> data;
    private final EmailServiceImpl emailService;

    public LoginServiceImpl(UserRepository userRepository, LoginRepository loginRepository, UserServiceImpl userService, PasswordEncoder passwordEncoder
            , SecurityUtil jwtUtil, RegistrationServiceImpl registrationService, TempInMemoryDb tempInMemoryDb,
                            EmailServiceImpl emailServiceImpl){
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
        this.tempInMemoryDb = tempInMemoryDb;
        this.emailService = emailServiceImpl;
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto login) throws Exception {
        User dbUser = userRepository.findByEmail(login.getUsername());
        if(Objects.isNull(dbUser)){
            return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        if(!passwordEncoder.matches(login.getPassword().trim(), dbUser.getPassword())){
            return new ResponseEntity<>(Constants.WRONG_PASSWORD_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }
        final String token = jwtUtil.generateToken(dbUser);
        final String refreshToken = jwtUtil.refreshTokenGenerator(new HashMap<>(),dbUser);
        User response = UserMapper.UserResponseMapper(dbUser);
        return new ResponseEntity<>(new LoginResponse(response,token,refreshToken),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> initiateResetPassword(String email, ResetPasswordDto request) {
        //TODO:validate the operation with a session token just for resetting the password
        User dbUser = userRepository.findByEmail(email);
        if(Objects.isNull(dbUser)){
            return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        if(!StringUtils.equals(request.getConfirmPassword(), request.getNewPassword())){
            return new ResponseEntity<>(Constants.PASSWORD_MISMATCH,HttpStatus.BAD_REQUEST);
        }
        RegisterMerchantDto registerMerchantDto = RegistrationMapper.registrationMapper(dbUser);
        registerMerchantDto.setPassword(request.getNewPassword());
        registerMerchantDto.setToken(Utils.emailVerificationOtp());
        tempInMemoryDb.saveData(registerMerchantDto.getEmail(),registerMerchantDto);
        sendOTP(registerMerchantDto,Constants.RESET_PASSWORD,resetPasswordTemplate);
        return  new ResponseEntity<>("Token sent to User",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(String email, String token) {
        RegisterMerchantDto user = (RegisterMerchantDto) tempInMemoryDb.getData(email);
        if(Objects.isNull(user)){
            return new ResponseEntity<>(Constants.USER_NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        if(!StringUtils.equals(String.valueOf(user.getToken()),token)){
            return new ResponseEntity<>(Constants.INVALID_TOKEN_PROVIDED,HttpStatus.BAD_REQUEST);
        }
        User updatedUser = RegistrationMapper.merchantRegistrationMapper(user);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        loginRepository.updatePassword(updatedUser.getEmail(),updatedUser.getPassword());
        return new ResponseEntity<>(Constants.SUCCESSFUL,HttpStatus.OK);
    }

    public void sendOTP (RegisterMerchantDto tempRegData, String subject, String template){
        data = new HashMap<>();
        data.put("token", String.valueOf(tempRegData.getToken()));
        data.put("name", tempRegData.getBusinessName());
        String emailBody = emailService.prepareEmailTemplate(template, data);
        emailService.sendEmail(tempRegData.getEmail(), emailBody,
                subject);
        log.info("OTP sent to user email: {}",tempRegData.getEmail());
    }

//    public static void main(String[] args) {
//        String passcode = "juve";
//        String passcode2 = "juve";
//        PasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
//        String newPasscode = passwordEncoder1.encode(passcode);
//        if(!passwordEncoder1.matches(passcode,newPasscode)){
//            System.out.println("Entered");
//        }
//        else System.out.println("Exited");
//        //System.out.println("newPasscode: " + newPasscode);
//    }

}
