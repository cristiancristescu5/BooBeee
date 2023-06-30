package com.example.demo.Author;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    public AuthorEntity findByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from author where id = '" + aLong + "'")) {
            var rs = preparedStatement.executeQuery();
            return rs.next() ? new AuthorEntity(
                    rs.getLong(1),
                    rs.getDate(2),
                    rs.getDate(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)) : null;
        } catch (SQLException e) {
            connection.rollback();
            connection.close();
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<AuthorEntity> findAll() throws SQLException {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from author")) {
            {
                var rs = statement.executeQuery();
                while (rs.next()) {
                    authorEntities.add(new AuthorEntity(
                            rs.getLong(1),
                            rs.getDate(2),
                            rs.getDate(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7))
                    );
                }
                return authorEntities;
            }
        }
    }

    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "delete from author where id =?"
                     )) {
            preparedStatement.setLong(1, aLong);
            var rs = preparedStatement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }

    public AuthorEntity updateById(Long id, AuthorEntity authorEntity) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "update author set birthday =  ?, deathday = ?, name = ?, description = ?, pictureurl=?, websiteurl=? where id = ?"
        )){
            statement.setDate(1, authorEntity.getBirthday());
            statement.setDate(2, authorEntity.getDeathday());
            statement.setString(3, authorEntity.getName());
            statement.setString(4, authorEntity.getDescription());
            statement.setString(5, authorEntity.getPictureurl());
            statement.setString(6, authorEntity.getWebsiteurl());
            statement.executeUpdate();
            connection.commit();
            connection.close();
            return authorEntity;
        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
            connection.close();
            return  null;
        }
    }

    public AuthorEntity create(AuthorEntity author) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO author (birthDay, deathDay, name, description, pictureURL, websiteURL) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setDate(1, author.getBirthday());
            preparedStatement.setDate(2, author.getDeathday());
            preparedStatement.setString(3, author.getName());
            preparedStatement.setString(4, author.getDescription());
            preparedStatement.setString(5, author.getPictureurl());
            preparedStatement.setString(6, author.getWebsiteurl());
            preparedStatement.executeUpdate();
            connection.close();
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
