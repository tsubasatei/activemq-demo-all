package com.xt.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class QueueProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destinationQueue;

    // 发送文本消息
    public void sendTextMessage (String text) {
        jmsTemplate.send(destinationQueue, session -> session.createTextMessage(text));
    }
}
