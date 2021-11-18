package com.winterchen.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Value("${rpc.client.balance}")
    private String balance;
    @Value("${rpc.client.discovery-addr}")
    private String discovery;
    @Bean
    public String sss(){
        return new String("");
    }
}
