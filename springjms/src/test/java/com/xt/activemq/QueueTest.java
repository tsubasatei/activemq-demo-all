package com.xt.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:beans-jms.xml")
public class QueueTest {

    @Autowired
    private QueueProducer queueProducer;

    @Test
    public void testSend () {
        queueProducer.sendTextMessage("SpringJms-点对点");
    }
}
