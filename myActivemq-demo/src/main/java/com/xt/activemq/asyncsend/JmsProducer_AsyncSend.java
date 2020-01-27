package com.xt.activemq.asyncsend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * 生产者
 */
public class JmsProducer_AsyncSend {

    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616";
//    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616?jms.useAsyncSend=true";
    public static final String QUEUE_NAME = "queue-AsyncSend";

    public static void main(String[] args) throws JMSException {
        // 1. 创建连接工厂 ActiveMQConnectionFactory, 采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        activeMQConnectionFactory.setAlwaysSyncSend(true); // 异步发送
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
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        // 6. 通过使用 messageProducer 生产3条消息发送到 MQ 的队列里面
        TextMessage message = null;
        for (int i = 1; i <=3 ; i++) {
            // 7. 创建消息, 类比学生按照老师要求写好的面试题消息
            message = session.createTextMessage("message---" + i);// 理解为一个字符串
            message.setJMSMessageID(UUID.randomUUID().toString() + "---orderAsyncSend");
            String msgId = message.getJMSMessageID();

            // 8. 通过 messageProducer 发送给 mq
            activeMQMessageProducer.send(message, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgId + " has been send ok");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println(msgId + " fail to send to mq");
                }
            });
        }

        // 9. 关闭资源
        activeMQMessageProducer.close();
        session.close();
        connection.close();

        System.out.println("*** 消息发送到 MQ 完成");

    }
}

