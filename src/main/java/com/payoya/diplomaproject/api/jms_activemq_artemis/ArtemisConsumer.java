package com.payoya.diplomaproject.api.jms_activemq_artemis;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
class which listening queue jms.queue.destination from file application.properties for receiving messages
 and processing with them
 */

@Component
@Profile("prod")
public class ArtemisConsumer {

    @JmsListener(destination = "${jms.queue.destination}")
    public void receive(String msg) {
        System.out.println("Got Message: " + msg + LocalDateTime.now());
    }

}
