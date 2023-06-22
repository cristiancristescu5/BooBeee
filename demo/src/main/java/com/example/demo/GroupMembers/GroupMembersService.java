package com.example.demo.GroupMembers;

public class GroupMembersService {
    private final GroupMembersRepository groupMembersRepository = new GroupMembersRepository();

    public GroupMembersEntity addMember(Long groupId, Long userId){
        return groupMembersRepository.save(new GroupMembersEntity(userId, groupId));
    }
}
