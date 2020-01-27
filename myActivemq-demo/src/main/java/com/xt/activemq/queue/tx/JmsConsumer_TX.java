package com.xt.activemq.queue.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消费者
 */
public class JmsConsumer_TX {
    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("*** 我是 2 号消费者");

        // 1. 创建连接工厂 ActiveMQConnectionFactory, 采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2. 通过连接工厂，获得连接 Connection 并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        /**
         * 3. 创建会话 Session
         * 两个参数：
         *      第一个参数：事务
         *      第二个参数：签收
         */
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        // 4. 创建目的地（具体是队列还是主题 topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5. 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (textMessage != null) {
                System.out.println("***消费者接收到的事务消息：" + textMessage.getText());
                textMessage.acknowledge();
            } else {
                break;
            }
        }


        messageConsumer.close();
        session.commit();
        session.close();
        connection.close();
    }
}


