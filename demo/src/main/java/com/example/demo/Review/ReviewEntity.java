package com.example.demo.Review;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReviewEntity implements Serializable {
    private long id;
    private Integer rating;
    private String description;
    private Timestamp createdat;
    private Long userId;
    private Long bookId;
    public ReviewEntity(String description, Integer rating){
        this.rating = rating;
        this.description = description;
        this.createdat = new Timestamp(System.currentTimeMillis());
    }

    public ReviewEntity(long id, Integer rating, String description, Timestamp createdat, Long userId, Long bookId) {
        this.id = id;
        this.rating = rating;
        this.description = description;
        this.createdat = createdat;
        this.userId = userId;
        this.bookId = bookId;
    }

    public ReviewEntity(){
        this.createdat = new Timestamp(System.currentTimeMillis());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewEntity that = (ReviewEntity) o;

        if (id != that.id) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        return result;
    }
}
