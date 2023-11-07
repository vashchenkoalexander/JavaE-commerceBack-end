package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Review;
import com.payoya.diplomaproject.api.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewRestController {

    private ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;

    }

    @PostMapping("/new")
    public Review createNew(@RequestBody Review review, @RequestParam Long productId ){
        return reviewService.createReview(review, productId);
    }

}
