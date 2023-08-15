package com.payoya.diplomaproject.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "username is required")
    @Column(name = "username")
    private String username;

    @NotNull(message = "password is required")
    @Column(name = "password")
    private String password;

    @Size(min = 2, message = "First Name must be at least 2 chars")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "Last Name must be at least 2 chars")
    @Column(name = "last_name")
    private String lastName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName= lastName;
    }

    public String getLastName(){
        return this.lastName;
    }
}
