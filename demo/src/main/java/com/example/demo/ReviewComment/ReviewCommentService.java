package com.example.demo.ReviewComment;

import com.example.demo.Comment.CommentEntity;
import com.example.demo.Comment.CommentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewCommentService {
    private final CommentRepository commentRepository = new CommentRepository();
    private final ReviewCommentRepository reviewCommentRepository = new ReviewCommentRepository();
    public List<CommentEntity> findCommentsByReviewId(Long reviewId) throws SQLException {
        List<ReviewCommentEntity> reviewCommentEntities =reviewCommentRepository.findByReviewId(reviewId);
        List<CommentEntity> commentEntities = new ArrayList<>();
        for(ReviewCommentEntity r : reviewCommentEntities){
            commentEntities.add(commentRepository.findByID(r.getCommentId()));
        }
        return commentEntities;
    }
    public ReviewCommentEntity addCommentToReview(Long userId, Long reviewID, Long commentId) throws SQLException{
        return reviewCommentRepository.create(new ReviewCommentEntity(userId, reviewID, commentId));
    }
}
