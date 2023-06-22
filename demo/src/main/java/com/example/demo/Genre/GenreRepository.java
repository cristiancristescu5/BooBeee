package com.example.demo.Genre;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class GenreRepository implements Repository<GenreEntity, Long>{
    @Override
    public GenreEntity findByID(Long aLong) {
        var query = DataBase.getInstance().createNamedQuery("genres.findById", GenreEntity.class);
        query.setParameter(1, aLong);
        return query.getSingleResult();
    }
    @Override
    public List<GenreEntity> findAll() {
        return DataBase.getInstance().createNamedQuery("genres.findAll", GenreEntity.class)
                .getResultList();
    }
    public GenreEntity findByName(String name){
        return DataBase.getInstance().createNamedQuery("genres.findByName", GenreEntity.class)
                .setParameter(1, name)
                .getSingleResult();
    }
    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        GenreEntity g = findByID(aLong);
        DataBase.getInstance().remove(g);
        DataBase.getInstance().getTransaction().commit();
    }

    public GenreEntity updateById(Long id, GenreEntity genre) {
        DataBase.getInstance().getTransaction().begin();
        GenreEntity g = findByID(id);
        g.setName(genre.getName());
        DataBase.getInstance().persist(g);
        DataBase.getInstance().getTransaction().commit();
        return g;
    }
}
