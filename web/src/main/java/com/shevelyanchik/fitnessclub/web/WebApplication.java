package com.shevelyanchik.fitnessclub.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZoneOffset;
import java.util.TimeZone;

@EntityScan(basePackages = "com.shevelyanchik.fitnessclub")
@EnableJpaRepositories(basePackages = "com.shevelyanchik.fitnessclub")
@SpringBootApplication(scanBasePackages = "com.shevelyanchik.fitnessclub")
@EnableDiscoveryClient
public class WebApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
        SpringApplication.run(WebApplication.class, args);
    }

}
