package com.xt.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BootMqTopicProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqTopicProducerApplication.class, args);
    }

}
