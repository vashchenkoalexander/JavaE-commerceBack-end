package com.payoya.diplomaproject.api.jms_activemq_artemis;

import com.payoya.diplomaproject.api.entity.Order;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.mongoDB.BaseEntity;
import com.payoya.diplomaproject.api.mongoDB.BaseEntityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
/*
Class for working with JMS ActiveMQ Artemis for produce message to server ActiveMQ Artemis
 properties for this work in application.properties
 */
@Component
@Profile("prod")
public class ArtemisProducer {

    private final JmsTemplate jmsTemplate;
    private final BaseEntityService baseEntityService;

    public ArtemisProducer(JmsTemplate jmsTemplate, BaseEntityService baseEntityService) {
        this.jmsTemplate = jmsTemplate;
        this.baseEntityService = baseEntityService;
    }

    @Value("${jms.queue.destination}")
    String destinationQueue;

    public void send(String msg){
        jmsTemplate.convertAndSend(destinationQueue, msg);
    }

    public void sendOrderedItems(Order order){
        BaseEntity entity = new BaseEntity("order created", order);
        jmsTemplate.convertAndSend(destinationQueue, order.getId() + " =order");
        baseEntityService.saveEntity(entity);
    }

    public void sendMessage(User user){
        BaseEntity entity = new BaseEntity("message sent", user);
        jmsTemplate.convertAndSend(destinationQueue, user + " =user");
        baseEntityService.saveEntity(entity);
    }

}
