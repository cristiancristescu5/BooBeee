package com.example.demo.BookGenres;

import jakarta.persistence.*;

import java.io.Serializable;

public class BookGenresEntity implements Serializable {
    private long id;
    private Long bookId;
    private Long genreId;

    public BookGenresEntity(long id, Long bookId, Long genreId) {
        this.id = id;
        this.bookId = bookId;
        this.genreId = genreId;
    }
    public BookGenresEntity(){}

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

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookGenresEntity that = (BookGenresEntity) o;

        if (id != that.id) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (genreId != null ? !genreId.equals(that.genreId) : that.genreId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        return result;
    }
}
