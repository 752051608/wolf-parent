package com.wolf.config.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
public class ActiveMqConfig {

    @Value("${amq.sessionCacheSize}")
    private Integer sessionCacheSize;
    @Value("${amq.broker.url}")
    private String brokerURL;
    @Value("${amq.username}")
    private String userName;
    @Value("${amq.password}")
    private String password;

    @Bean
    public CachingConnectionFactory createCachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerURL);
        activeMQConnectionFactory.setUserName(userName);
        activeMQConnectionFactory.setPassword(password);
        cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(sessionCacheSize);
        return cachingConnectionFactory;
    }

}
