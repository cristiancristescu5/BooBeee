package com.example.demo.BookGenres;

import com.example.demo.Book.BookEntity;
import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class BookGenresRepository implements Repository<BookGenresEntity, Long>{

    @Override
    public BookGenresEntity findByID(Long aLong) {
        return null;
    }

    @Override
    public List<BookGenresEntity> findAll() {
        return null;
    }

    public List<BookGenresEntity> findByGenreId(Long aLong) {
        return DataBase.getInstance().createNamedQuery("book_genre.findByGenreId", BookGenresEntity.class)
                .setParameter(1, aLong)
                .getResultList();
    }
    public BookGenresEntity findByBookId(Long aLong) {
        return DataBase.getInstance().createNamedQuery("book_genre.findByBookId", BookGenresEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }
    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        BookGenresEntity b = findByID(aLong);
        DataBase.getInstance().remove(b);
        DataBase.getInstance().getTransaction().commit();
    }

}
