package com.xt.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 主题持久化：
 * 1. 一定要先运行一次消费者，等于向 MQ 注册，类似我订阅了这个主题
 * 2. 然后再运行生产者发送信息
 * 3. 此时无论消费者是否在线，都会接收到，不在线的话，下次连接的时候，会把没有收过的消息都接受下来。
 */
public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.239.13:61616";
    public static final String TOPIC_NAME = "jdbc01-topic-persist";

    public static void main(String[] args) throws JMSException {

        System.out.println("*** z4");

        // 1. 创建连接工厂 ActiveMQConnectionFactory, 采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2. 通过连接工厂，获得连接 Connection 并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z4");

        /**
         * 3. 创建会话 Session
         * 两个参数：
         *      第一个参数：事务
         *      第二个参数：签收
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 创建目的地（具体是队列还是主题 topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 主题订阅者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");
        // 启动
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("***收到的持久化 topic 消息是：" + textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();
    }
}


