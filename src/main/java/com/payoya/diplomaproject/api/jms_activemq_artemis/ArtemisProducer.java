package com.payoya.diplomaproject.api.jms_activemq_artemis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisProducer {

    private JmsTemplate jmsTemplate;

    public ArtemisProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Value("${jms.queue.destination}")
    String destinationQueue;



    public void send(String msg){
        jmsTemplate.convertAndSend(destinationQueue, msg);
    }

}
