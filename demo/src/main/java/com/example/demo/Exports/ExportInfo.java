package com.example.demo.Exports;

public class ExportInfo {
    private String bookName;
    private String bookDescription;
    private String bookAuthor;
    private String isbn;
    private String genre;
    private String status;

    public ExportInfo(String bookName, String bookDescription, String bookAuthor, String isbn, String genre, String status) {
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.bookAuthor = bookAuthor;
        this.isbn = isbn;
        this.genre = genre;
        this.status = status;
    }
    public ExportInfo(){}
    @Override
    public String toString() {
        return bookName + ", " + bookDescription + ", " + bookAuthor +", "+isbn+", "+genre+", "+status;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
