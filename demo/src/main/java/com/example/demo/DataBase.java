package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataBase {
    private static final EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("default");
    private static EntityManager entityManager = emf.createEntityManager();
    public static EntityManager getInstance(){
        return entityManager;
    }
}
