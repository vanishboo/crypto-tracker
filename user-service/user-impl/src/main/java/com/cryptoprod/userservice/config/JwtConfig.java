package com.cryptoprod.userservice.config;

import com.cryptoprod.common.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}
