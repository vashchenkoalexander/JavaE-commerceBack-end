package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Product;
import com.payoya.diplomaproject.api.entity.Review;
import com.payoya.diplomaproject.api.repository.IProductRepository;
import com.payoya.diplomaproject.api.repository.IReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private IReviewRepository reviewRepository;

    private IProductRepository productRepository;

    public ReviewService(IReviewRepository reviewRepository, IProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public Review createReview(Review review, Long productId){

        Product product = productRepository.findById(productId).orElse(null);

        if(product == null){
            throw new IllegalStateException("you cant write review for this product because he doesn't exist");
        }

        review.setProduct(product);
        reviewRepository.save(review);

        product.calculateMedianRating();
        productRepository.save(product);

        return review;
    }

}
