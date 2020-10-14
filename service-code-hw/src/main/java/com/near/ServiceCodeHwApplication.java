package com.near;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // 开启 Feign 客户端功能
public class ServiceCodeHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCodeHwApplication.class, args);
    }

}
