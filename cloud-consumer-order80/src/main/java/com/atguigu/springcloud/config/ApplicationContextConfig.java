package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    //@LoadBalanced //由于在这里使用自己手写的Ribbon负载均衡算法，所以注释该注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
