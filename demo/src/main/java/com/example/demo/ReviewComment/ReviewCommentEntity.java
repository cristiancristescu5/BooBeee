package com.example.demo.ReviewComment;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "review_comment", schema = "public", catalog = "BooDB")
@NamedQuery(name = "reviewComments.findByUserId", query = "select a from ReviewCommentEntity a where a.userId = ?1 ")
@NamedQuery(name = "reviewComments.findByReviewId", query = "select a from ReviewCommentEntity a where a.reviewId = ?1 ")
@NamedQuery(name = "reviewComments.findAll", query = "select a from ReviewCommentEntity a")
@NamedQuery(name = "reviewComments.findByIDs", query = "select a from ReviewCommentEntity a where a.userId =?1 and a.reviewId = ?2 and a.commentId = ?3")
public class ReviewCommentEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "review_id", nullable = true)
    private Long reviewId;
    @Basic
    @Column(name = "comment_id", nullable = true)
    private Long commentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
