package com.payoya.diplomaproject.api.entity;

import com.payoya.diplomaproject.api.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username is required")
    @Size(min = 2, message = "Username must be at least 2 chars")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 2, message = "Password must be at least 2 chars")
    @Column(name = "password")
    private String password;

    @Size(min = 2, message = "First Name must be at least 2 chars")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "Last Name must be at least 2 chars")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "role is required")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "date_of_create")
    private Date dateOfCreate;

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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singleton(role)); //IS THIS WORKING PROPER???!?!?!?
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Date getDateOfCreate(){
        if(dateOfCreate == null){
            dateOfCreate = new Date();
            return dateOfCreate;
        }
        return dateOfCreate;
    }

    public void setDateOfCreate(){
        if(dateOfCreate == null){
            dateOfCreate = new Date();
        }
    }
}