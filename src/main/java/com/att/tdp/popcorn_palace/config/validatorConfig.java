package com.att.tdp.popcorn_palace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import jakarta.validation.Validator;

@Configuration
public class validatorConfig {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
