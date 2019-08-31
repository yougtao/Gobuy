package com.gobuy.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.gobuy.item.mapper")
public class GobuyItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(GobuyItemApplication.class, args);
    }
}
