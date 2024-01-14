package com.booking.smartx.services;

import com.booking.smartx.dto.responseDto.LoginResponse;
import com.booking.smartx.entities.Login;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface UserService{

    UserDetailsService userDetailsService();
}
