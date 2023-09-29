package com.payoya.diplomaproject.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @Nullable
    private String title;
    @Column(name = "body")
    @Nullable
    private String body;

    @Column(name = "tag")
    private String tag;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull(message = "user is required")
    @JsonBackReference(value = "user-posts")
    private User user;

    public Long getId() {
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBody(){
        return this.body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getTag(){
        return this.tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public LocalDateTime getDateOfCreate(){
        return this.dateOfCreate;
    }

    public void setDateOfCreate(LocalDateTime localDateTimeNow){
        this.dateOfCreate = localDateTimeNow;
    }

    public Long getUserIdBelongsToPost(){
        return this.user.getId();
    }

}
