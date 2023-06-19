package com.example.demo.Group;

import java.util.List;

public class GroupService {
    private static final GroupRepository groupRepository = new GroupRepository();
    public GroupEntity addGroup(GroupEntity groupEntity){
        return groupRepository.save(groupEntity);
    }
    public GroupEntity getGroupById(Long id){
        var group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Group does not exist in database");
        }else{
            return group;
        }
    }
    public void deleteGroup(Long id){
        GroupEntity group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        groupRepository.deleteByID(id);
    }
    public GroupEntity updateGroup(Long id, GroupEntity groupEntity){
        GroupEntity group = groupRepository.findByID(id);
        if(group == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        group = groupEntity;
        return groupRepository.updateById(id, group);
    }
    public List<GroupEntity> getAllGroups(){
        return groupRepository.findAll();
    }
}
