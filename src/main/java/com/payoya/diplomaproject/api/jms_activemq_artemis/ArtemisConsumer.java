package com.payoya.diplomaproject.api.jms_activemq_artemis;

import org.springframework.cglib.core.Local;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ArtemisConsumer {

    @JmsListener(destination = "${jms.queue.destination}")
    public void receive(String msg) {
        System.out.println("Got Message: " + msg + LocalDateTime.now());
    }

}
