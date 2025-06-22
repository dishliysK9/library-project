package com.dishoo.library_project.responsemodels;

import com.dishoo.library_project.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReviewResponse {

    private Long id;
    private String userEmail;
    private String date;
    private Double rating;
    private Long bookId;
    private String reviewDescription;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.userEmail = review.getUser().getEmail();
        this.date = review.getDate().toString();
        this.rating = review.getRating();
        this.bookId = review.getBook().getId();
        this.reviewDescription = review.getReviewDescription();
    }
}


