package com.example.demo.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private static ReviewRepository reviewRepository = new ReviewRepository();

    public List<ReviewEntity> findReviewsByBookId(Long bookId){
        return reviewRepository.findByBookId(bookId);
    }
}
