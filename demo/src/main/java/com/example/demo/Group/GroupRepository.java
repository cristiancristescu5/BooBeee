package com.example.demo.Group;

import com.example.demo.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    public GroupEntity findByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select * from group_ where id = '" + aLong + "'")) {
            connection.close();
            return resultSet.next() ? new GroupEntity(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getTimestamp(4),
                    resultSet.getInt(5)): null;
        } catch (SQLException e) {
            connection.close();
            return null;
        }
    }

    public void deleteByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from group_ where id = ?")) {
            statement.setLong(1, aLong);
            var rs = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<GroupEntity> findAll() throws SQLException{
        Connection connection = DataBase.getConnection();
        List<GroupEntity> groups = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "select * from group_"
                )) {
            while (resultSet.next()) {
                groups.add(new GroupEntity(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4),
                        resultSet.getInt(5)));
            }
            connection.close();
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return null;
        }
    }

    public GroupEntity updateById(Long id, GroupEntity group) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update group_ set name = ?, description = ?, createdat = ?, membwers_count = ? where id = ?")) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setTimestamp(3, group.getCreatedat());
            preparedStatement.setInt(4, group.getMembersCount());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            connection.close();
            return group;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return null;
        }
    }
    public GroupEntity findByName(String name1) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
               "select * from group_ where name = '" + name1 + "'"
        )){
            return resultSet.next() ? new GroupEntity(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getTimestamp(4),
                    resultSet.getInt(5)): null;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            connection.rollback();
            return null;
        }
    }
    public void updateMembersCount(String name1) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "update group_ set members_count = members_count+1 where name = ? "
        )){
            preparedStatement.setString(1, name1);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }
    public void updateMembersCountMinus(String name) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "update group_ set members_count = members_count-1 where name = ? "
        )){
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }

    public GroupEntity create(GroupEntity group) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO group_ (name, description, createdAt, members_count) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setTimestamp(3, group.getCreatedat());
            preparedStatement.setInt(4, group.getMembersCount());
            preparedStatement.executeUpdate();
            connection.close();
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
