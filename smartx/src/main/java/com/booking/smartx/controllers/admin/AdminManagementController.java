package com.booking.smartx.controllers.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminManagementController {

    @GetMapping("/test")
    public String adminControllerTest(){
        return "Admin access Granted";
    }
}
