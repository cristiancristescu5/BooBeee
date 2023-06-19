package com.example.demo.Group;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class GroupRepository implements Repository<GroupEntity, Long> {
    @Override
    public GroupEntity findByID(Long aLong) {
        return DataBase.getInstance().createNamedQuery("groups.findById", GroupEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }
    @Override
    public void deleteByID(Long aLong) {
        DataBase.getInstance().getTransaction().begin();
        GroupEntity g = findByID(aLong);
        DataBase.getInstance().remove(g);
        DataBase.getInstance().getTransaction().commit();
    }

    @Override
    public List<GroupEntity> findAll() {
        return DataBase.getInstance().createNamedQuery("groups.findAll", GroupEntity.class)
                .getResultList();
    }

    public GroupEntity updateById(Long id, GroupEntity group) {
        DataBase.getInstance().getTransaction().begin();
        GroupEntity g = findByID(id);
        g.setName(group.getName());
        g.setDescription(group.getDescription());
        g.setMembersCount(group.getMembersCount());
        DataBase.getInstance().persist(g);
        DataBase.getInstance().getTransaction().commit();
        return g;
    }
}
