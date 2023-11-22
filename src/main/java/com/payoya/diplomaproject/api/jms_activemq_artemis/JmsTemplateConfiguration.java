package com.payoya.diplomaproject.api.jms_activemq_artemis;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JmsTemplateConfiguration {

    @Bean
    public org.springframework.jms.core.JmsTemplate jmsTemplate() {
        org.springframework.jms.core.JmsTemplate jmsTemplate = new org.springframework.jms.core.JmsTemplate();
        ConnectionFactory ActiveMQConnectionFactory = new ActiveMQConnectionFactory();
        jmsTemplate.setConnectionFactory(ActiveMQConnectionFactory);
        return jmsTemplate;
    }

}
