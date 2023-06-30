package com.example.demo.BookAuthors;

import jakarta.persistence.*;

import java.io.Serializable;

public class BookAuthorsEntity implements Serializable {
    private long id;
    private Long bookId;
    private Long authorId;

    public BookAuthorsEntity(long id, Long bookId, Long authorId) {
        this.id = id;
        this.bookId = bookId;
        this.authorId = authorId;
    }
    public BookAuthorsEntity(){}

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookAuthorsEntity that = (BookAuthorsEntity) o;

        if (id != that.id) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        return result;
    }
}
