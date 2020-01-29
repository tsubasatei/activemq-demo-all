package com.xt.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class QueueProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queueTextDestination;

    @Autowired
    private Destination topicTextDestination;

    /**
     * 发送文本消息
     * @param text
     */
    public void sendTextMessage(String text) {
        jmsTemplate.send(queueTextDestination, session -> {
            TextMessage textMessage = session.createTextMessage(text);
            return textMessage;
        });
    }

    public void sendTextMessageTopic(String text) {
        jmsTemplate.send(topicTextDestination, session -> {
            TextMessage textMessage = session.createTextMessage(text);
            return textMessage;
        });
    }
}
