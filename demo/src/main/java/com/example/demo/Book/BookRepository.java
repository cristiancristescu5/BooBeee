package com.example.demo.Book;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class BookRepository implements Repository<BookEntity, Long> {
    @Override
    public BookEntity findByID(Long aLong) {
        return DataBase.getInstance().find(BookEntity.class, aLong);
    }

    @Override
    public List<BookEntity> findAll() {
        var query = DataBase.getInstance().createNamedQuery("book.findAll", BookEntity.class);
        return query.getResultList();

    }
    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        BookEntity b = findByID(aLong);
        DataBase.getInstance().remove(b);
        DataBase.getInstance().getTransaction().commit();
    }

    public BookEntity updateById(Long id, BookEntity bookEntity){
        DataBase.getInstance().getTransaction().begin();
        BookEntity book = findByID(id);
        book.setTitle(bookEntity.getTitle());
        book.setIsbn(bookEntity.getIsbn());
        book.setDescription(bookEntity.getDescription());
        book.setPicture(bookEntity.getPicture());
        DataBase.getInstance().persist(book);
        DataBase.getInstance().getTransaction().commit();
        return book;
    }
}
