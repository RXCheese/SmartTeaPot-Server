package com.springboot.smartteapot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartteapotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartteapotApplication.class, args);
    }

}
