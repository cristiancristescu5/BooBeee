package com.example.demo.Comment;

import com.example.demo.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from comment_ where id = ?")) {
            statement.setLong(1, aLong);
            var rs = statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public CommentEntity findByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select * from comment_ where id = '" + aLong + "'")) {
            return resultSet.next() ? new CommentEntity(resultSet.getLong(1),
            resultSet.getString(2),
            resultSet.getTimestamp(3)): null;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }


    public List<CommentEntity> findAll() throws SQLException {
        Connection connection = DataBase.getConnection();
        List<CommentEntity> comments = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "select * from comment_"
                )) {
            while (resultSet.next()) {
                comments.add(new CommentEntity(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getTimestamp(3)));
            }

            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return null;
        }
    }
    public CommentEntity updateById(Long id, CommentEntity commentEntity) throws SQLException{
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update comment_ set description=? where id = ?")) {
            preparedStatement.setString(1, commentEntity.getDescription());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

            return commentEntity;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return null;
        }
    }

    public CommentEntity create(CommentEntity comment) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO comment_ (description, createdat) VALUES (?, ?)")) {
            preparedStatement.setString(1, comment.getDescription());
            preparedStatement.setTimestamp(2, comment.getCreatedat());
            preparedStatement.executeUpdate();

            return comment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
