package com.xt.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class SpringMQ_Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {

        ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans.xml");
        SpringMQ_Producer producer = ioc.getBean("springMQ_Producer", SpringMQ_Producer.class);
        producer.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("***Spring 和 ActiveMQ 整合的 case for MessageListener 333....");
            return textMessage;
        });
        System.out.println("******send task over");
    }
}
