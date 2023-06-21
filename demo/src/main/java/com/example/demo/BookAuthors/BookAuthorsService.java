package com.example.demo.BookAuthors;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.Author.AuthorRepository;
import com.example.demo.Author.AuthorService;
import com.example.demo.DataBase;

import javax.xml.crypto.Data;
import java.util.List;

public class BookAuthorsService {
    BookAuthorsRepository bookAuthorsRepository = new BookAuthorsRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    public AuthorEntity getAuthorByBookId(Long bookId){
//        BookAuthorsEntity bookAuthorsEntity = DataBase.getInstance()
//                .createNamedQuery("book_authors.findByBookId")
//                .setParameter(1, bookId)
//                .getSingleResult();
        List<BookAuthorsEntity> bookAuthorsEntities = bookAuthorsRepository.findByBookId(bookId);
        if(bookAuthorsEntities.size()==0){
            return null;
        }
        return authorRepository.findByID(bookAuthorsEntities.get(0).getAuthorId());
    }
}
