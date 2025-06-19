package com.dishoo.library_project.controller;

import com.dishoo.library_project.entity.Review;
import com.dishoo.library_project.requestmodels.ReviewRequest;
import com.dishoo.library_project.service.ReviewService;
import com.dishoo.library_project.utils.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader("Authorization") String token, @RequestParam Long bookId) throws Exception {
        var userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value ="Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception {
        var userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}
