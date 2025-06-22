package com.dishoo.library_project.service;

import com.dishoo.library_project.dao.BookRepository;
import com.dishoo.library_project.dao.ReviewRepository;
import com.dishoo.library_project.dao.UserRepository;
import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.entity.Review;
import com.dishoo.library_project.entity.User;
import com.dishoo.library_project.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found (shouldn't happen we have filter ensuring there is user in db"));

        Book book = bookRepository.findById(reviewRequest.getBookId())
                .orElseThrow(() -> new Exception("Book not found"));

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(reviewRequest.getRating());
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(Object::toString).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long bookId) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found (shouldn't happen we have filter ensuring there is user in db"));
        Review validateReview = reviewRepository.findByUserIdAndBookId(user.getId(), bookId);

        return validateReview != null;
    }
    public List<Review> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
