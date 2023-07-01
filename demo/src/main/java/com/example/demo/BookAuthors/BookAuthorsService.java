package com.example.demo.BookAuthors;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.Author.AuthorRepository;
import com.example.demo.Author.AuthorService;
import com.example.demo.DataBase;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;

public class BookAuthorsService {
    BookAuthorsRepository bookAuthorsRepository = new BookAuthorsRepository();

    AuthorRepository authorRepository = new AuthorRepository();

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
}
