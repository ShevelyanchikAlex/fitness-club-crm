package com.shevelyanchik.fitnessclub.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.shevelyanchik.fitnessclub.userservice")
@EnableJpaRepositories(basePackages = "com.shevelyanchik.fitnessclub.userservice")
@SpringBootApplication(scanBasePackages = "com.shevelyanchik.fitnessclub.userservice")
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
