package com.payoya.diplomaproject.api.mongoDB;

import jakarta.persistence.Id;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Profile("prod")
public class BaseEntity {

    @Id
    private String id;

    private String message;
    private LocalDateTime timeStamp;
    private Object object;

    public BaseEntity(String message, Object object){
        this.message = message;
        this.object = object;
        this.timeStamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
