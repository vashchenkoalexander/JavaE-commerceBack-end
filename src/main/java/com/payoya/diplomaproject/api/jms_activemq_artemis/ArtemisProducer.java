package com.payoya.diplomaproject.api.jms_activemq_artemis;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.mongoDB.BaseEntity;
import com.payoya.diplomaproject.api.mongoDB.BaseEntityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisProducer {

    private JmsTemplate jmsTemplate;
    private BaseEntityService baseEntityService;

    public ArtemisProducer(JmsTemplate jmsTemplate, BaseEntityService baseEntityService) {
        this.jmsTemplate = jmsTemplate;
        this.baseEntityService = baseEntityService;
    }

    @Value("${jms.queue.destination}")
    String destinationQueue;

    public void send(String msg){
        jmsTemplate.convertAndSend(destinationQueue, msg);
    }

    public void sendMessage(User user){
        BaseEntity entity = new BaseEntity("message sent", user);
        jmsTemplate.convertAndSend(destinationQueue, user + " =user");
        baseEntityService.saveEntity(entity);
    }

}
