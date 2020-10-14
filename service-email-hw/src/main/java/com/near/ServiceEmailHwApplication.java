package com.near;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceEmailHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmailHwApplication.class, args);
    }

}
