package com.near.eurekahw8762;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaHw8762Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaHw8762Application.class, args);
    }

}
