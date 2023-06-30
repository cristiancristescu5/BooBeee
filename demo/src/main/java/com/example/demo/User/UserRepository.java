package com.example.demo.User;

import com.example.demo.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository{

    public UserEntity findByID(Long aLong) throws SQLException{
            Connection connection = DataBase.getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                         "select * from users where id = '" + aLong + "'")) {

                return resultSet.next() ? new UserEntity(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getTimestamp(6),
                        resultSet.getTimestamp(7),
                        resultSet.getBoolean(8)) : null;
            } catch (SQLException e) {

                return null;
            }
    }
    public UserEntity findByEmail(String email)throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from users where email = ?"
        )){
            statement.setString(1,email);
            var rs = statement.executeQuery();
            return rs.next() ? new UserEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getTimestamp(6),
                    rs.getTimestamp(7),
                    rs.getBoolean(8)
            ):null;
        }catch (SQLException e){
            e.printStackTrace();

            return null;
        }
    }
    public UserEntity findByName(String name1) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from users where name = ?"
        )){
            statement.setString(1, name1);
            var rs = statement.executeQuery();
            return  rs.next() ? new UserEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getTimestamp(6),
                    rs.getTimestamp(7),
                    rs.getBoolean(8)) : null;
        }catch (SQLException e){

            return null;
        }
    }

    public void deleteByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "delete from users where id = ?"
        )){
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.commit();

        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }

    }

    public List<UserEntity> findAll() throws SQLException {
        Connection connection = DataBase.getConnection();
        List<UserEntity> users = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(
                        "select * from users"
                )) {
            while (rs.next()) {
                users.add(new UserEntity(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getTimestamp(7),
                        rs.getBoolean(8)));
            }
            return users;
        }catch(SQLException e){
            e.printStackTrace();

            return null;
        }
    }

    public UserEntity updateById(Long id, UserEntity userEntity)throws SQLException{
        Connection connection = DataBase.getConnection();
        if(userEntity.getName()!=null && userEntity.getEmail()!=null && userEntity.getPictureurl()!=null){
            try(PreparedStatement statement = connection.prepareStatement(
                    "update users set name = ?, email = ?, pictureurl = ? where id = ?"
            )){
                statement.setString(1, userEntity.getName());
                statement.setString(2, userEntity.getEmail());
                statement.setString(3, userEntity.getPictureurl());
                statement.setLong(4, id);
                statement.executeUpdate();
                return findByEmail(userEntity.getEmail());
            }
        }else{
            return null;
        }
    }

    public UserEntity create(UserEntity user) throws SQLException {
        Connection connection = DataBase.getConnection();
        user.setVerified(false);
        user.setUpdatedat(new Timestamp(System.currentTimeMillis()));
        user.setCreatedat(new Timestamp(System.currentTimeMillis()));
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users (name, email, password, pictureurl, createdat, updatedat, verified) values (?,?,?,?,?,?,?)"
        )){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPictureurl());
            preparedStatement.setTimestamp(5, user.getCreatedat());
            preparedStatement.setTimestamp(6, user.getUpdatedat());
            preparedStatement.setBoolean(7, user.getVerified());
            preparedStatement.executeUpdate();

            return findByEmail(user.getEmail());
        }catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
            return null;
        }
    }
}
