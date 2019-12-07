package com.xt.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者
 */
public class JmsProducer {

    public static final String ACTIVEMQ_URL = "nio://192.168.239.13:61608";
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "transport";

    public static void main(String[] args) throws JMSException {
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

        // 5. 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        // 非持久化
//        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // 持久化
//        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 6. 通过使用 messageProducer 生产3条消息发送到 MQ 的队列里面
        for (int i = 1; i <=3 ; i++) {
            // 7. 创建消息, 类比学生按照老师要求写好的面试题消息
            TextMessage textMessage = session.createTextMessage("textMessage msg---" + i);// 理解为一个字符串

            // 设置消息属性
            textMessage.setStringProperty("c01", "vip");

            // 8. 通过 messageProducer 发送给 mq
            messageProducer.send(textMessage);

//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setString("k1", "mapMessage---v" + i);
//            messageProducer.send(mapMessage);


        }

        // 9. 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*** 消息发送到 MQ 完成");

    }
}

