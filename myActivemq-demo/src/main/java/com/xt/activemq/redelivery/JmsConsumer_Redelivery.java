package com.xt.activemq.redelivery;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.io.IOException;

/**
 * @author xt
 * @create 2020/1/27 23:19
 * @Desc
 */
public class JmsConsumer_Redelivery {
    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616";
    public static final String QUEUE_NAME = "queue-redelivery";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 消费重试机制
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(1000L);
            if (null != textMessage) {
                System.out.println("消费者接收到的消息：" + textMessage.getText());
            } else {
                break;
            }
        }

//        session.commit();
        connection.close();
        session.close();
    }
}
