package com.example.demo.Genre;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    public GenreEntity findByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from genre where id = ?"
        )){
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            return rs.next() ? new GenreEntity(aLong, rs.getString(2)) : null;
        }catch (SQLException e){
            connection.close();
            e.printStackTrace();
            return null;
        }
    }

    public List<GenreEntity> findAll() throws SQLException {
        List<GenreEntity> genreEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from genre"
        )){
            var rs = statement.executeQuery();
            while(rs.next()){
                genreEntities.add(new GenreEntity(rs.getLong(1), rs.getString(2)));
            }
            connection.close();
            return genreEntities;
        }catch (SQLException e){
            e.printStackTrace();
            connection.close();
            return null;
        }
    }
    public GenreEntity findByName(String name)throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from genre where name = ?"
        )){
            statement.setString(1, name);
            var rs = statement.executeQuery();
            return rs.next() ? new GenreEntity(rs.getLong(1), rs.getString(2)) : null;
        }
    }

    public void deleteByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "delete from genre where id = ?"
        )){
            statement.setLong(1, aLong);
            var rs = statement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            connection.close();
        }
    }

    public GenreEntity updateById(Long id, GenreEntity genre) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "update genre set name = ? where id = ?"
        )){
            statement.setLong(2, id);
            statement.setString(1, genre.getName());
            statement.executeUpdate();
            return genre;
        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
            return null;
        }
    }

    public GenreEntity create(GenreEntity genre) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO genre (name) VALUES (?)")) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
            connection.close();
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
