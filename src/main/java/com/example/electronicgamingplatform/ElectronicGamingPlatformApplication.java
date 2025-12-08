package com.example.electronicgamingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ElectronicGamingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicGamingPlatformApplication.class, args);
    }

}
