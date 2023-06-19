package com.example.demo.Comment;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class CommentRepository implements Repository<CommentEntity, Long>{
    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        CommentEntity c = findByID(aLong);
        DataBase.getInstance().remove(c);
        DataBase.getInstance().getTransaction().commit();
    }

    @Override
    public CommentEntity findByID(Long aLong) {
        return DataBase.getInstance().createNamedQuery("comment.findById", CommentEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }

    @Override
    public List<CommentEntity> findAll() {
        return DataBase.getInstance().createNamedQuery("comment.findAll", CommentEntity.class)
                .getResultList();
    }
    public CommentEntity updateById(Long id, CommentEntity commentEntity){
        DataBase.getInstance().getTransaction().begin();
        CommentEntity comment = findByID(id);
        comment.setDescription(commentEntity.getDescription());
        DataBase.getInstance().persist(comment);
        DataBase.getInstance().getTransaction().commit();
        return comment;
    }
}
