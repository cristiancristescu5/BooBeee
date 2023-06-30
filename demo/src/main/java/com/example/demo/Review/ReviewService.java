package com.example.demo.Review;

import com.example.demo.Book.BookEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private static ReviewRepository reviewRepository = new ReviewRepository();

    public List<ReviewEntity> findReviewsByBookId(Long bookId) throws SQLException {
        return reviewRepository.findByBookId(bookId);
    }

    public ReviewEntity create(ReviewEntity entity) throws SQLException {
        return reviewRepository.create(entity);
    }

    public Float getBookRating(Long bookId) throws SQLException {
        List<ReviewEntity> reviewEntities = findReviewsByBookId(bookId);
        float media = 0.0f;
        if (reviewEntities.size() > 0) {
            for (ReviewEntity e : reviewEntities) {
                media += (float) e.getRating();
            }
        }
        return media / reviewEntities.size();
    }

    public void deleteReview(Long id) throws SQLException {
        ReviewEntity review = reviewRepository.findByID(id);
        if(review==null){
            throw new IllegalArgumentException("Review does not exists");
        }
        reviewRepository.deleteByID(id);
    }

    public ReviewEntity updateById(Long id, ReviewEntity review) throws SQLException{
        ReviewEntity reviewEntity = reviewRepository.findByID(id);
        if(reviewEntity==null){
            throw new IllegalArgumentException("Entity not found");
        }
        reviewEntity = review;
        return reviewRepository.updateById(id, review);
    }
    public ReviewEntity findByID(Long id) throws SQLException{
        var review = reviewRepository.findByID(id);
        if(review == null){
            throw new IllegalArgumentException("Review does not exist");
        }else{
            return review;
        }


    }
}
