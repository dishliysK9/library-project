package com.dishoo.library_project.dao;

import com.dishoo.library_project.entity.Checkout;
import com.dishoo.library_project.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    Review findByUserIdAndBookId(Long userId, Long bookId);

    List<Review> findByBookId(Long bookId);

    @Modifying
    @Query("delete from Review where book_id in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
