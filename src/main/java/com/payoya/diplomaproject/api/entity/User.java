package com.payoya.diplomaproject.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.payoya.diplomaproject.api.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

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

    @Email
    @NotBlank(message = "email address is required")
    @Column(name = "email_address")
    private String emailAddress;

    @Size(min = 2, message = "First Name must be at least 2 chars")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "Last Name must be at least 2 chars")
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-posts")
    private List<Post> postsList;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference(value = "user-shoppingCart")
    private Cart shoppingCart;

    private String activationToken;

    @OneToMany
    @JsonManagedReference(value = "orders-user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-listProducts")
    private List<Product> productList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-shipping_addresses")
    private List<ShippingAddress> shippingAddressList;

    private Boolean isActive = false;

    public User(){}

    public User(Long id, String username, String password, String firstName, String lastName, String emailAddress){
        this.id = id;
        this.username= username;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress=emailAddress;
        this.dateOfCreate = LocalDateTime.now();
    }

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
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }

    public Role getRole() {
        return role;
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

    public LocalDateTime getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(LocalDateTime localDateTime) {
        this.dateOfCreate = localDateTime;
    }

    public byte[] getImage(){
        return this.image;
    }

    public void setImage(byte[] image){
        this.image = image;
    }

    public List<Post> getPostsList(){
        return this.postsList;
    }

    public void setPostsList(List<Post> posts){
     this.postsList = posts;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", dateOfCreate=" + dateOfCreate +
                '}';
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Boolean getIsActive(){
        return this.isActive;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }

    public Cart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Cart cart) {
        this.shoppingCart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<ShippingAddress> getShippingAddressList() {
        return shippingAddressList;
    }

    public void setShippingAddressList(List<ShippingAddress> shippingAddressList) {
        this.shippingAddressList = shippingAddressList;
    }
}
