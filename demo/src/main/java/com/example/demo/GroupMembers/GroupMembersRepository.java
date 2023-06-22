package com.example.demo.GroupMembers;

import com.example.demo.DataBase;
import com.example.demo.Group.GroupEntity;
import com.example.demo.Repository;

import java.util.List;

public class GroupMembersRepository implements Repository<GroupMembersEntity, Long>{

    public List<GroupMembersEntity> findByUserId(Long aLong){
        return DataBase.getInstance().createNamedQuery("groupMembers.findByUserId", GroupMembersEntity.class)
                .setParameter(1, aLong)
                .getResultList();
    }
    public List<GroupMembersEntity> findByGroupId(Long aLong){
        return DataBase.getInstance().createNamedQuery("groupMembers.findByGroupId", GroupMembersEntity.class)
                .setParameter(1, aLong).getResultList();
    }

    public GroupMembersEntity addGroupMember(Long groupId, Long userId){
        return save(new GroupMembersEntity(userId, groupId));
    }

    @Override
    public GroupMembersEntity findByID(Long aLong) {
        return null;
    }

    @Override
    public List<GroupMembersEntity> findAll() {
        return DataBase.getInstance().createNamedQuery("groupMembers.findAll", GroupMembersEntity.class)
                .getResultList();
    }

    @Override
    public void deleteByID(Long aLong) {

    }

    public void deleteByUserId(Long id){
        GroupMembersEntity g1 = DataBase.getInstance().find(GroupMembersEntity.class, id);
        DataBase.getInstance().getTransaction().begin();
        DataBase.getInstance().remove(g1);
        DataBase.getInstance().getTransaction().commit();
    }

    public GroupEntity findIdByGroupName(String name){
        return DataBase.getInstance().createNamedQuery("groups.findIdByName", GroupEntity.class)
                .setParameter(1, name)
                .getSingleResult();
    }
    public GroupMembersEntity findByUserIdAndGroupId(Long userId, Long groupId){
        return DataBase.getInstance().createNamedQuery("groupMembers.findByGroupIdAndUserId", GroupMembersEntity.class)
                .setParameter(1, userId)
                .setParameter(2, groupId)
                .getSingleResult();
    }
}
