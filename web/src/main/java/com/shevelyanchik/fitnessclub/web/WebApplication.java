package com.shevelyanchik.fitnessclub.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.shevelyanchik.fitnessclub")
@EnableJpaRepositories(basePackages = "com.shevelyanchik.fitnessclub")
@SpringBootApplication(scanBasePackages = "com.shevelyanchik.fitnessclub")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
