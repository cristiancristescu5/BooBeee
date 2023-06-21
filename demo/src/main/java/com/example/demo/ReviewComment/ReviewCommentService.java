package com.example.demo.ReviewComment;

import com.example.demo.Comment.CommentEntity;
import com.example.demo.Comment.CommentRepository;
import com.example.demo.DataBase;

import java.util.ArrayList;
import java.util.List;

public class ReviewCommentService {
    private final CommentRepository commentRepository = new CommentRepository();
    private final ReviewCommentRepository reviewCommentRepository = new ReviewCommentRepository();
    public List<CommentEntity> findCommentsByReviewId(Long reviewId){
        List<ReviewCommentEntity> reviewCommentEntities = DataBase.getInstance()
                .createNamedQuery("reviewComments.findByReviewId")
                .setParameter(1, reviewId)
                .getResultList();
        List<CommentEntity> commentEntities = new ArrayList<>();
        for(ReviewCommentEntity r : reviewCommentEntities){
            commentEntities.add(commentRepository.findByID(r.getCommentId()));
        }
        return  commentEntities;

    }
}
