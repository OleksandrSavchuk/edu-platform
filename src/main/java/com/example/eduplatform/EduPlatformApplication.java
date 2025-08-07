package com.example.eduplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EduPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduPlatformApplication.class, args);
    }

}
