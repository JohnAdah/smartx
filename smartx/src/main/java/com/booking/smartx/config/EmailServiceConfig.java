package com.booking.smartx.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class EmailServiceConfig {
    @Bean
    @Qualifier("customFreeMarkerConfig")
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration(){
        FreeMarkerConfigurationFactoryBean freeMarkerConfig = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfig.setTemplateLoaderPath("classpath:/templates/");
        return freeMarkerConfig;
    }
}
