package com.near;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayHwApplication.class, args);
    }

}
