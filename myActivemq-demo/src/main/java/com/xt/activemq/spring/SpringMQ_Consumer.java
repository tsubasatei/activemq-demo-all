package com.xt.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {

        ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans.xml");
        SpringMQ_Consumer consumer = ioc.getBean("springMQ_Consumer", SpringMQ_Consumer.class);
        String returnValue = (String) consumer.jmsTemplate.receiveAndConvert();
        System.out.println("***消费者收到的消息：" + returnValue);
    }

}
