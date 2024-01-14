package com.booking.smartx.config;

import com.booking.smartx.dao.TempInMemoryDb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public TempInMemoryDb tempDb(){
        return new TempInMemoryDb();
    }
}
