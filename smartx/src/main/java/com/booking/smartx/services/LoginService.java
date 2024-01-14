package com.booking.smartx.services;

import com.booking.smartx.dto.requestDto.LoginRequestDto;
import com.booking.smartx.dto.responseDto.LoginResponse;
import com.booking.smartx.entities.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface LoginService {
    ResponseEntity<?> login(LoginRequestDto login) throws Exception;
}
