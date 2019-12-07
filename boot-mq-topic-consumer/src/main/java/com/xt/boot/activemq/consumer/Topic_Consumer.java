package com.xt.boot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Topic_Consumer {

    @JmsListener(destination = "${myTopic}")
    public void receiveMessage(TextMessage textMessage) throws JMSException {
        System.out.println("消费者收到订阅的主题消息：" + textMessage.getText());

    }
}
