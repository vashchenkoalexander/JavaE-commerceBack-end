package com.payoya.diplomaproject.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "title must be present")
    private String title;

    @Column(name = "body_title")
    @NotNull(message = "bodyTitle must be present")
    @Lob
    @Size(max = 20000)
    private String bodyTitle;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "tags")
    @NotNull(message = "tags must be present")
    private String tags;

    @Column(name = "date_of_create")
    private LocalDateTime dateofCreate;

    @ManyToOne
    @JsonBackReference
    private User user;

    public Long getId() {
        return id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getBodyTitle() {
        return bodyTitle;
    }

    public void setBodyTitle(String bodyTitle){
        this.bodyTitle=bodyTitle;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image){
        this.image=image;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags(){
        return this.tags;
    }

    public LocalDateTime getDateofCreate(){
        return dateofCreate;
    }

    public void setDateofCreate(LocalDateTime dateofCreate){
        this.dateofCreate = dateofCreate;
    }

}
