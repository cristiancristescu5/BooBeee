package com.example.demo.BookStatus;

import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookRepository;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class BookStatusService {
    private final BookStatusRepository bookStatusRepository = new BookStatusRepository();
    private final BookRepository bookRepository = new BookRepository();
    private final UserRepository userRepository = new UserRepository();
    public List<BooksWithStatuses> getAllBooks(String email){
        List<BooksWithStatuses> books = new ArrayList<>();
        UserEntity user = userRepository.findByEmail(email);
        List<BookStatusEntity> bookStatusEntities = bookStatusRepository.findByUserId(user.getId());
        for(BookStatusEntity b : bookStatusEntities){
            BooksWithStatuses book = new BooksWithStatuses();
            book.setStatus(b.getStatus());
            book.setBookID(b.getBookId());
            BookEntity bookEntity = bookRepository.findByID(b.getBookId());
            book.setIsbn(bookEntity.getIsbn());
            book.setName(bookEntity.getTitle());
            book.setImageURL(bookEntity.getPicture());
            books.add(book);
        }
        return books;
    }
    public BookStatusEntity getBookStatusByBookIdAndStatusAndUserEmail(Long bookId, String email){
        UserEntity user = userRepository.findByEmail(email);
        return bookStatusRepository.findByAllCredentials(bookId, user.getId());
    }
    public BookStatusEntity addBookStatus(BookStatusEntity bookStatusEntity){
        return bookStatusRepository.save(bookStatusEntity);
    }
    public BookStatusEntity updateBookStatus(String status, Long bookId, Long userId){
        return bookStatusRepository.updateBookStatus(status, userId, bookId);
    }
}
