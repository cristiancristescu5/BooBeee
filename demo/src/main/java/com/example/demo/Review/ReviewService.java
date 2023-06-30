package com.example.demo.Review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private static ReviewRepository reviewRepository = new ReviewRepository();

    public List<ReviewEntity> findReviewsByBookId(Long bookId)throws SQLException{
        return reviewRepository.findByBookId(bookId);
    }
    public ReviewEntity create(ReviewEntity entity)throws SQLException {
        return reviewRepository.create(entity);
    }
}
