package com.booking.smartx.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    private String newPassword;
    private String confirmPassword;
}
