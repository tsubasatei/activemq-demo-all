package com.xt.boot.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class MyMQConfig {

    @Value("${myqueue}")
    private String myQueue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(myQueue);
    }

}
