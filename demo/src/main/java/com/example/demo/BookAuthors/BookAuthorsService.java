package com.example.demo.BookAuthors;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.Author.AuthorRepository;
import com.example.demo.Author.AuthorService;
import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookRepository;
import com.example.demo.DataBase;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookAuthorsService {
    private final BookAuthorsRepository bookAuthorsRepository = new BookAuthorsRepository();

    private final AuthorRepository authorRepository = new AuthorRepository();
    private final BookRepository bookRepository = new BookRepository();

    public AuthorEntity getAuthorByBookId(Long bookId) throws SQLException {
        List<BookAuthorsEntity> bookAuthorsEntities = bookAuthorsRepository.findByBookId(bookId);
        if(bookAuthorsEntities.size()==0){
            return null;
        }
        return authorRepository.findByID(bookAuthorsEntities.get(0).getAuthorId());
    }
    public BookAuthorsEntity create(BookAuthorsEntity bookAuthorsEntity)throws SQLException{
        return bookAuthorsRepository.create(bookAuthorsEntity);
    }
    public List<BookEntity> getBookByAuthorId(Long authorId)throws SQLException{
        List<BookAuthorsEntity> bookAuthorsEntities = bookAuthorsRepository.findByAuthorId(authorId);
        List<BookEntity> bookEntities = new ArrayList<>();
        for(BookAuthorsEntity b : bookAuthorsEntities){
            bookEntities.add(bookRepository.findByID(b.getBookId()));
        }
        return bookEntities;
    }
}
