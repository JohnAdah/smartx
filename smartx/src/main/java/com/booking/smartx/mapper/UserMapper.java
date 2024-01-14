package com.booking.smartx.mapper;

import com.booking.smartx.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User UserResponseMapper(User dto){
        return User.builder().email(dto.getEmail())
                .address(dto.getAddress())
                .businessName(dto.getBusinessName())
                .role(dto.getRole())
                .numberOfEmployees(dto.getNumberOfEmployees())
                .phoneNumber(dto.getPhoneNumber())
                .postCode(dto.getPostCode())
                .build();
    }
}
