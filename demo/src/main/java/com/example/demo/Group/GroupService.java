package com.example.demo.Group;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class GroupService {
    private static final GroupRepository groupRepository = new GroupRepository();

    public GroupEntity addGroup(GroupEntity groupEntity) throws SQLException{
        return groupRepository.create(groupEntity);
    }
    public GroupEntity getGroupById(Long id)throws SQLException {
        var group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Group does not exist in database");
        }else{
            return group;
        }
    }
    public void deleteGroup(Long id)throws SQLException{
        GroupEntity group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        groupRepository.deleteByID(id);
    }
    public GroupEntity updateGroup(Long id, GroupEntity groupEntity)throws SQLException{
        GroupEntity group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        group = groupEntity;
        return groupRepository.updateById(id, group);
    }
    public GroupEntity findByName(String name)throws SQLException{
        return groupRepository.findByName(name);
    }
    public List<GroupEntity> getAllGroups()throws SQLException{
        return groupRepository.findAll();
    }
}
