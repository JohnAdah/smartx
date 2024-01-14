package com.booking.smartx.controllers.merchant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/merchant")
public class MerchantManagementController {

    @GetMapping("/checking")
    public String merchantControllerTest(){
        return "merchant access Granted";
    }

}
