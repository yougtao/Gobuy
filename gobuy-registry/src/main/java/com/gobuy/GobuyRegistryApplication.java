package com.gobuy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GobuyRegistryApplication
{
    public static void main(String[] args) {
        SpringApplication.run(GobuyRegistryApplication.class, args);
    }
}
