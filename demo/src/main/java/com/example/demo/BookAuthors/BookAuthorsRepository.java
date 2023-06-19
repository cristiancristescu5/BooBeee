package com.example.demo.BookAuthors;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class BookAuthorsRepository implements Repository<BookAuthorsEntity, Long> {
    @Override
    public BookAuthorsEntity findByID(Long aLong) {
        return null;
    }

    @Override
    public List<BookAuthorsEntity> findAll() {
        return null;
    }

    @Override
    public <S extends BookAuthorsEntity> S save(S entity) {
        return Repository.super.save(entity);
    }

    @Override
    public void deleteByID(Long aLong) {

    }

    public List<BookAuthorsEntity> findByAuthorId(Long aLong){
        return DataBase.getInstance().createNamedQuery("book_authors.findByAuthorID", BookAuthorsEntity.class)
                .setParameter(1, aLong)
                .getResultList();
    }
    public List<BookAuthorsEntity> findByBookId(Long aLong){
        return DataBase.getInstance().createNamedQuery("book_authors.findByBookId", BookAuthorsEntity.class)
                .setParameter(1, aLong)
                .getResultList();
    }
}
