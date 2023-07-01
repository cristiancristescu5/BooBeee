package com.example.demo.Admin;

import java.sql.Date;
import java.util.UUID;

public class BookGenreAuthor {
    private Long authorId;
    private String genre;
    private String title;
    private String isbn;
    private String description;
    private String picture;

    public BookGenreAuthor(Long authorId, String genre, String title, String isbn, String description, String picture) {
        this.authorId = authorId;
        this.genre = genre;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.picture = picture;
    }
    public BookGenreAuthor(){}

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
