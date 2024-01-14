package com.booking.smartx.controllers.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/customer")
public class CustomerManagementController {

        @GetMapping("/testing")
        public String customerControllerTest(){
            return "Customer access Granted";
        }
}
