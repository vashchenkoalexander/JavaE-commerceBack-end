package com.payoya.diplomaproject.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

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
    @Size(max = 20000)
    private String bodyTitle;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "imageList-product")
    private List<Image> imageList;

    @Column(name = "tags")
    @NotNull(message = "tags must be present")
    private String tags;

    @Column(name = "date_of_create")
    private LocalDateTime dateofCreate;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "orderItems-product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "reviewList-product")
    private List<Review> reviews;

    private Double medianRating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-listProducts")
    @Nullable
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

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getMedianRating() {
        return medianRating;
    }

    public void setMedianRating(Double medianRating) {
        this.medianRating = medianRating;
    }

    public void calculateMedianRating(){
        List<Integer> ratingValues = reviews.stream()
                .map(review -> review.getRating())
                .sorted().toList();
        medianRating = ratingValues.stream().mapToInt(a -> a).summaryStatistics().getAverage();
    }
}
