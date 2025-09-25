package com.dishoo.library_project.controller;

import com.dishoo.library_project.requestmodels.ReviewRequest;
import com.dishoo.library_project.responsemodels.HistoryResponse;
import com.dishoo.library_project.responsemodels.ReviewResponse;
import com.dishoo.library_project.service.ReviewService;
import com.dishoo.library_project.utils.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("https://library-service-react-961766531040.europe-west1.run.app")
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

    @GetMapping("/search/findByBookId")
    public List<ReviewResponse> getReviewsByBookId(@RequestParam Long bookId) {
        return reviewService.getReviewsByBookId(bookId)
                .stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }
}
