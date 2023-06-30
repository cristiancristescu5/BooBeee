package com.example.demo.ReviewComment;

import jakarta.persistence.*;

import java.io.Serializable;

public class ReviewCommentEntity implements Serializable {
    private Long id;
    private Long userId;
    private Long reviewId;
    private Long commentId;

    public ReviewCommentEntity(Long id, Long userId, Long reviewId, Long commentId) {
        this.id = id;
        this.userId = userId;
        this.reviewId = reviewId;
        this.commentId = commentId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public ReviewCommentEntity(Long userId, Long reviewId, Long commentId){
        this.userId = userId;
        this.reviewId = reviewId;
        this.commentId = commentId;
    }
    public ReviewCommentEntity(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewCommentEntity that = (ReviewCommentEntity) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (reviewId != null ? !reviewId.equals(that.reviewId) : that.reviewId != null) return false;
        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (reviewId != null ? reviewId.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        return result;
    }
}
