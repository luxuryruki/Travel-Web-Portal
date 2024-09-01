package com.example.travewebportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TravelWebPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelWebPortalApplication.class, args);
    }

}
