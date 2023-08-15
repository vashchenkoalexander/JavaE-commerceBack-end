package com.payoya.diplomaproject.mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_test")
public class UserTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=5, message = "username must be at least 5 characters long")
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min=7, message = "email must be at least 7 characters long")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 2, message = "at least 2 chars")
    @Column(name = "gender")
    private String gender;

    public UserTest(){}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
