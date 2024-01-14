package com.booking.smartx.services.impl;

import com.booking.smartx.dao.UserRepository;
import com.booking.smartx.dto.requestDto.LoginRequestDto;
import com.booking.smartx.dto.responseDto.LoginResponse;
import com.booking.smartx.entities.User;
import com.booking.smartx.mapper.UserMapper;
import com.booking.smartx.security.SecurityUtil;
import com.booking.smartx.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private final UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final SecurityUtil jwtUtil;

    public LoginServiceImpl(UserRepository userRepository, UserServiceImpl userService, AuthenticationManager authenticationManager, SecurityUtil jwtUtil){
        this.userRepository = userRepository;
        this.userService = userService;
        //this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto login) throws Exception {
        User dbUser = userRepository.findByEmail(login.getUsername());
        final String token = jwtUtil.generateToken(dbUser);
        final String refreshToken = jwtUtil.refreshTokenGenerator(new HashMap<>(),dbUser);
        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        User response = UserMapper.UserResponseMapper(dbUser);
        return new ResponseEntity<>(new LoginResponse(response,token,refreshToken),HttpStatus.OK);
    }

//    private void authenticate(String username, String password) throws Exception{
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//        } catch (DisabledException e) {
//            throw new Exception("USER DISABLED",e);
//        } catch (BadCredentialsException e){
//            throw new CredentialException("INVALID CREDENTIALS");
//        }
//    }
}
