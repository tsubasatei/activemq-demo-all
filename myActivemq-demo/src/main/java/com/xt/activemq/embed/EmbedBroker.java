package com.xt.activemq.embed;

import org.apache.activemq.broker.BrokerService;

/**
 * broker: activemq服务器实例
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        /**
         * ActiveMQ 也支持在 vm 中通信基于嵌入式的 broker
         */
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
