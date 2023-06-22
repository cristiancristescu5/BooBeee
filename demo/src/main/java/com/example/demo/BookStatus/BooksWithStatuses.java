package com.example.demo.BookStatus;

import com.example.demo.Book.BookEntity;

public class BooksWithStatuses {
    private String name;
    private String isbn;
    private String imageURL;
    private String status;
    private Long bookID;

    public BooksWithStatuses(String name, String isbn, String imageURL, String status) {
        this.name = name;
        this.isbn = isbn;
        this.imageURL = imageURL;
        this.status = status;
    }
    public BooksWithStatuses(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
}
