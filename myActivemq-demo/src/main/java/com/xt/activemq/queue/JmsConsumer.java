package com.xt.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消费者
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "nio://192.168.239.13:61608";
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "transport";

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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 创建目的地（具体是队列还是主题 topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5. 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);


        /**
         * 方式一：同步阻塞方式（receive()）
         * 订阅者或接收者调用 messageConsumer 的 receive() 方法来接收消息，receive()在能够接收到消息之前（或超市之前）将一直阻塞
         */
        /*
        while (true) {
            // 6. 接收消息
//            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L); // 超时自动关闭
            if (textMessage != null) {
                System.out.println("消费者接收到的消息：" + textMessage.getText());
            } else {
                break;
            }
        }

        // 7. 关闭资源
        messageConsumer.close();
        session.close();
        connection.close();

        */

        /**
         * 方式二：通过监听的方式来消费消息
         * 异步非阻塞方式（监听器 onMessage()）
         * 订阅者或接收者调用 messageConsumer 的 setMessageListener(MessageListener listener) 注册一个消息监听器，
         * 当消息到达之后，系统自动调用监听器 MessageListener 的 onMessage(Message message) 方法
         */
        messageConsumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者接收到的消息：MessageListener broker --- " + textMessage.getText());
//                    System.out.println("****消费者接收到的消息属性：MessageListener --- " + textMessage.getStringProperty("c01"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
//            if (message != null && message instanceof MapMessage) {
//                MapMessage mapMessage = (MapMessage) message;
//                try {
//                    System.out.println("****消费者接收到的消息：MessageListener --- " + mapMessage.getString("k1"));
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

        /**
         * 问题：
         * 1. 先生产，只启动 1 号消费者。问 1 号消费者能消费消息吗？ Y
         *
         * 2. 先生产，先启动 1 号消费者，再启动 2 号消费者，问 2号消费者能消费消息吗？
         *      2.1) 1 号可以消费消息 Y
         *      2.2) 2 号可以消费消息 N(消息已经被 1 号消费完)
         *
         * 3. 先启动 2 个消费者，再生产 6 条消息，问 消费情况如何
         *      3.1) 2 个消费者都有 6 条
         *      3.2) 先到先得，6 条全部给一个
         *      3.2) 一人一半  Y
         * 4. MQ 挂了，那么消息的持久化和丢失情况分别如何？
         */
    }
}


