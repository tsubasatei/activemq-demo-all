package com.xt.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class TestQueue {

    @Autowired
    private QueueProducer queueProducer;

    @Test
    public void testQueueSend () {
        queueProducer.sendTextMessage("Spring Jms -- 点对点");
    }
    @Test
    public void testQueueReceive () {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueueSendTopic () {
        queueProducer.sendTextMessageTopic("Spring Jms -- 发布/订阅");
    }
}
