package com.example.demo.GroupMembers;

import com.example.demo.DataBase;
import com.example.demo.Group.GroupEntity;
import com.example.demo.Group.GroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMembersRepository {
    private final GroupRepository groupRepository = new GroupRepository();

    public List<GroupMembersEntity> findByUserId(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        List<GroupMembersEntity> groupMembersEntities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_members where user_id = ? "
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while (rs.next()) {
                groupMembersEntities.add(new GroupMembersEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)));
            }
            connection.close();
            return groupMembersEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

    public List<GroupMembersEntity> findByGroupId(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        List<GroupMembersEntity> groupMembersEntities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_members where group_id = ? "
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while (rs.next()) {
                groupMembersEntities.add(new GroupMembersEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)));
            }
            connection.close();
            return groupMembersEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

    public GroupMembersEntity addGroupMember(Long groupId, Long userId) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into group_members(user_id, group_id) values(?, ?)"
        )) {
            statement.setLong(1, userId);
            statement.setLong(2, groupId);
            statement.executeUpdate();
            connection.close();
            return findByUserIdAndGroupId(userId, groupId);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }


    public GroupMembersEntity findByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_members where id = ?"
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            GroupMembersEntity e = rs.next() ? new GroupMembersEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)) : null;
            connection.close();
            return e;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }


    public List<GroupMembersEntity> findAll() throws SQLException {
        List<GroupMembersEntity> groupMembersEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_members"
        )) {
            var rs = statement.executeQuery();
            while (rs.next()) {
                groupMembersEntities.add(new GroupMembersEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)));
            }
            connection.close();
            return groupMembersEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }


    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from group_members where id = ?"
        )) {
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            connection.close();
        }
    }

    public void deleteByUserId(Long id) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from group_members where user_id = ?"
        )) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }

    public GroupEntity findIdByGroupName(String name) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_ where name = ?"
        )) {
            statement.setString(1, name);
            var rs = statement.executeQuery();
            GroupEntity g = rs.next() ? groupRepository.findByID(rs.getLong(1)) : null;
            connection.close();
            return g;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
            return null;
        }
    }

    public GroupMembersEntity findByUserIdAndGroupId(Long userId, Long groupId) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from group_members where user_id = ? and group_id = ?"
        )) {
            statement.setLong(1, userId);
            statement.setLong(2, groupId);
            var rs = statement.executeQuery();
            GroupMembersEntity g = rs.next() ? new GroupMembersEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)) : null;
            connection.close();
            return g;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

    public GroupMembersEntity create(GroupMembersEntity groupMembers) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO group_members (user_id, group_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, groupMembers.getUserId());
            preparedStatement.setLong(2, groupMembers.getGroupId());
            preparedStatement.executeUpdate();
            connection.close();
            return groupMembers;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

}
