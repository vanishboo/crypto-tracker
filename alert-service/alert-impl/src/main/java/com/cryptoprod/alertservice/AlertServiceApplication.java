package com.cryptoprod.alertservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(
        scanBasePackages = {
                "com.cryptoprod.alertservice",
                "com.cryptoprod.common"
        }
)
public class AlertServiceApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AlertServiceApplication.class, args);
    }
}
