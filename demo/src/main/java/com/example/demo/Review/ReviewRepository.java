package com.example.demo.Review;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import javax.xml.crypto.Data;
import java.util.List;

public class ReviewRepository implements Repository<ReviewEntity, Long>{
    @Override
    public ReviewEntity findByID(Long aLong) {
        return DataBase.getInstance().createNamedQuery("reviews.findById", ReviewEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }

    @Override
    public List<ReviewEntity> findAll() {
        return DataBase.getInstance().createNamedQuery("reviews.findAll", ReviewEntity.class)
                .getResultList();
    }

    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        ReviewEntity r = findByID(aLong);
        DataBase.getInstance().remove(r);
        DataBase.getInstance().getTransaction().commit();
    }

    public List<ReviewEntity> findByBookId(Long aLong){
        return DataBase.getInstance().createNamedQuery("reviews.findByBook", ReviewEntity.class)
                .setParameter(1, aLong)
                .getResultList();
    }
}
