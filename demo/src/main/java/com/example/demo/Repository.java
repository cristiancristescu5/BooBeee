package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface Repository <T extends Serializable, ID> {
    T findByID(ID id);

    List<T> findAll();

    default <S extends T> S save(S entity) {
        try {
            DataBase.getInstance().getTransaction().begin();
            DataBase.getInstance().persist(entity);
            DataBase.getInstance().getTransaction().commit();
        }catch (Exception e){
            DataBase.getInstance().getTransaction().rollback();
            throw new IllegalArgumentException("Failed to save entity");
        }
        return entity;
    }

    void deleteByID(ID id);
}
