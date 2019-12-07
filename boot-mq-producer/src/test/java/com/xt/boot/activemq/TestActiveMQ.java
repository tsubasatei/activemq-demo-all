package com.xt.boot.activemq;

import com.xt.boot.activemq.producer.Queue_Produce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class TestActiveMQ {

    @Autowired
    private Queue_Produce queue_produce;

    @Test
    public void testSend () {
        queue_produce.produceMessage();
    }
}
