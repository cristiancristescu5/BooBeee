package com.example.demo.User;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class UserRepository implements Repository<UserEntity, Long> {
    @Override
    public UserEntity findByID(Long aLong) {
        return DataBase.getInstance().createNamedQuery("user.findById", UserEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }

    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        UserEntity u = findByID(aLong);
        DataBase.getInstance().remove(u);
        DataBase.getInstance().getTransaction().commit();
    }
    @Override
    public List<UserEntity> findAll() {
        var query = DataBase.getInstance().createNamedQuery("user.findAll", UserEntity.class);
        return query.getResultList();
    }
    public UserEntity updateById(Long id, UserEntity userEntity){
        DataBase.getInstance().getTransaction().begin();
        UserEntity user = findByID(id);
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setPictureurl(userEntity.getPictureurl());
        DataBase.getInstance().persist(user);
        DataBase.getInstance().getTransaction().commit();
        return user;
    }
}

