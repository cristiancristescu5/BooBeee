package com.example.demo.Author;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class AuthorRepository implements Repository<AuthorEntity, Long> {
    @Override
    public AuthorEntity findByID(Long aLong) {
        return DataBase.getInstance().find(AuthorEntity.class, aLong);
    }

    @Override
    public List<AuthorEntity> findAll() {
        var query = DataBase.getInstance().createNamedQuery("author.findAll", AuthorEntity.class);
        return query.getResultList();
    }
    @Override
    public void deleteByID(Long aLong){
        DataBase.getInstance().getTransaction().begin();
        AuthorEntity a = findByID(aLong);
        DataBase.getInstance().remove(a);
        DataBase.getInstance().getTransaction().commit();
    }
    public AuthorEntity updateById(Long id, AuthorEntity authorEntity){
        AuthorEntity author = findByID(id);
        author.setName(authorEntity.getName());
        author.setBirthday(authorEntity.getBirthday());
        author.setDeathday(authorEntity.getDeathday());
        author.setWebsiteurl(authorEntity.getWebsiteurl());
        author.setPictureurl(authorEntity.getPictureurl());
        author.setDescription(authorEntity.getDescription());
        save(author);
        return author;
    }
}
