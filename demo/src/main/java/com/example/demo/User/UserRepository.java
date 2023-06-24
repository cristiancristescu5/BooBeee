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
    public UserEntity findByEmail(String email){
        return DataBase.getInstance().createNamedQuery("user.findByEmail", UserEntity.class)
                .setParameter(1, email)
                .getSingleResult();
    }
    public UserEntity findByName(String name){
        return DataBase.getInstance().createNamedQuery("user.findByName", UserEntity.class)
                .setParameter(1, name)
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
        if(userEntity.getName()!=null) {
            user.setName(userEntity.getName());
        }
        if(userEntity.getEmail()!=null) {
            user.setEmail(userEntity.getEmail());
        }
        if(userEntity.getPictureurl()!=null) {
            user.setPictureurl(userEntity.getPictureurl());
        }
        DataBase.getInstance().persist(user);
        DataBase.getInstance().getTransaction().commit();
        return user;
    }
}

