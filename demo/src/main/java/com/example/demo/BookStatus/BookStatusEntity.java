package com.example.demo.BookStatus;

import jakarta.persistence.*;

import java.io.Serializable;

public class BookStatusEntity implements Serializable {
    private long id;
    private Long bookId;
    private Long userId;
    private String status;

    public BookStatusEntity(Long userId, Long bookId, String bookStatus) {
        this.userId = userId;
        this.bookId = bookId;
        this.status = bookStatus;
    }

    public BookStatusEntity(long id, Long bookId, Long userId, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    public BookStatusEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStatusEntity that = (BookStatusEntity) o;

        if (id != that.id) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
