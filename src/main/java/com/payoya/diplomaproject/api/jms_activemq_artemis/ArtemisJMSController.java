package com.payoya.diplomaproject.api.jms_activemq_artemis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/jms")
public class ArtemisJMSController {

    private ArtemisProducer producer;

    ArtemisJMSController(ArtemisProducer producer){
        this.producer = producer;
    }

    @RequestMapping("/send")
    public String produce(@RequestParam("msg") String msg){
        producer.send(msg);
        return "Done" + LocalDateTime.now();
    }

}
