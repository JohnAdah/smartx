package com.booking.smartx.mapper;

import com.booking.smartx.dto.requestDto.RegisterMerchantDto;
import com.booking.smartx.dto.responseDto.RegistrationResponseDto;
import com.booking.smartx.entities.User;
import com.booking.smartx.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public static User merchantRegistrationMapper(RegisterMerchantDto dto){
        return User.builder().businessName(dto.getBusinessName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .postCode(dto.getPostCode())
                .businessType(dto.getBusinessType())
                .numberOfEmployees(Utils.validateIntegerInput(dto.getNumberOfEmployees()))
                .role("MERCHANT")
                .build();
    };

    public static RegistrationResponseDto merchantResponseMapper(User dto){
        return RegistrationResponseDto.builder().businessName(dto.getBusinessName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .postCode(dto.getPostCode())
                .businessType(dto.getBusinessType())
                .numberOfEmployees(Utils.validateIntegerInput(dto.getNumberOfEmployees()))
                .role(dto.getRole())
                .build();
    }
}
