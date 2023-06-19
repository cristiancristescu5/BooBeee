package com.example.demo.BookStatus;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class BookStatusRepository implements Repository<BookStatusEntity, Long> {
    public BookStatusEntity findByBookId(Long aLong){
        var query = DataBase.getInstance().createNamedQuery("book_status.findByBookId", BookStatusEntity.class);
        query.setParameter(1, aLong);
        return query.getSingleResult();
    }
    public BookStatusEntity findByUserId(Long aLong){
        var query = DataBase.getInstance().createNamedQuery("book_status.findByUserId", BookStatusEntity.class);
        query.setParameter(1, aLong);
        return query.getSingleResult();
    }
    public List<BookStatusEntity> findByUserIdAndStatus(String status, Long aLong){
        var query = DataBase.getInstance().createNamedQuery("book_status.findByUserIdAndStatus", BookStatusEntity.class);
        query.setParameter(1, aLong).setParameter(2, status);
        return query.getResultList();
    }

    @Override
    public BookStatusEntity findByID(Long aLong) {
        return null;
    }

    @Override
    public List<BookStatusEntity> findAll() {
        return null;
    }

    @Override
    public void deleteByID(Long aLong) {

    }
}
