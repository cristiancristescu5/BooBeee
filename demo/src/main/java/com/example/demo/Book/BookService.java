package com.example.demo.Book;

import java.util.List;

public class BookService {
    private static BookRepository bookRepository = new BookRepository();
    public BookEntity addBook(BookEntity bookEntity){
        return bookRepository.save(bookEntity);
    }
    public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
    }
    public BookEntity getBookById(Long id){
        var book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Book does not exist in database");
        }else{
            return book;
        }
    }
    public BookEntity updateBook(Long id, BookEntity bookEntity){
        BookEntity book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        book = bookEntity;
        return bookRepository.updateById(id, book);
    }
    public void deleteBook(Long id){
        BookEntity book = bookRepository.findByID(id);
        if(book == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        bookRepository.deleteByID(id);
    }
}
