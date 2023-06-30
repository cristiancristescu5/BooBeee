package com.example.demo.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private static final BookRepository bookRepository = new BookRepository();

    public BookEntity addBook(BookEntity bookEntity) throws SQLException {
        return bookRepository.create(bookEntity);
    }
    public List<BookEntity> getAllBooks() throws SQLException {
        return bookRepository.findAll();
    }
    public BookEntity getBookById(Long id) throws SQLException {
        var book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Book does not exist in database");
        }else{
            return book;
        }
    }
    public BookEntity updateBook(Long id, BookEntity bookEntity) throws SQLException {
        BookEntity book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        book = bookEntity;
        return bookRepository.updateById(id, book);
    }
    public void deleteBook(Long id) throws SQLException {
        BookEntity book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        bookRepository.deleteByID(id);
    }
}
