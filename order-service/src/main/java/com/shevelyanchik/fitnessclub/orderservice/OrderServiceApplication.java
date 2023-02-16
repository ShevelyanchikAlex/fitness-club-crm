package com.shevelyanchik.fitnessclub.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.shevelyanchik.fitnessclub.orderservice")
@EnableJpaRepositories(basePackages = "com.shevelyanchik.fitnessclub.orderservice")
@SpringBootApplication(scanBasePackages = "com.shevelyanchik.fitnessclub.orderservice")
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
