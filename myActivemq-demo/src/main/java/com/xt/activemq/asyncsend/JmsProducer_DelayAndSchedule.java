package com.xt.activemq.asyncsend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @author xt
 * @create 2020/1/27 21:29
 * @Desc
 */
public class JmsProducer_DelayAndSchedule {

    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616";
    public static final String QUEUE_NAME = "queue-delay";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        long delay = 3 * 1000;
        long period = 4 * 1000;
        int repeat = 5;
        for (int i = 1; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("delay message--" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("*** 消息发送到 MQ 完成");
    }
}
