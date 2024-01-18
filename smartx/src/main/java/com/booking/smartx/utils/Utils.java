package com.booking.smartx.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class Utils {

    private static Random random = new Random();

    public static int validateIntegerInput(int number){
        String value = String.valueOf(number);
        if(value.isEmpty() || value==null){
            return 0;
        }
        return number;
    }

    public static int emailVerificationOtp(){
        return random.nextInt(1000,9999);
    }

}
