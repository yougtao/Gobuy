package com.gobuy.order.config;


import com.gobuy.order.Utils.OrderIdGeneration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {
    @Bean
    public OrderIdGeneration idWorker(IdWorkerProperties prop) {
        return new OrderIdGeneration(prop.getWorkerId(), prop.getDatacenterId());
    }
}
