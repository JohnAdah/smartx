package com.booking.smartx.controllers;

import com.booking.smartx.dto.requestDto.LoginRequestDto;
import com.booking.smartx.services.LoginService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login (@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return loginService.login(loginRequestDto);
    }

    @GetMapping(value = "/user")
    public String loginControllerTest(){
        return "Testing the login controller";
    }

}