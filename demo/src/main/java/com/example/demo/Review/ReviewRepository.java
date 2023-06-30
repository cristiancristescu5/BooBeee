package com.example.demo.Review;

import com.example.demo.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository{

    public ReviewEntity findByID(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select * from review where id = '" + aLong + "'")) {

            ReviewEntity r = resultSet.next() ? new ReviewEntity(
                    resultSet.getLong(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getTimestamp(4),
                    resultSet.getLong(5),
                    resultSet.getLong(6)) : null;
            connection.close();
            return r;
        } catch (SQLException e) {
            connection.close();
            return null;
        }
    }


    public List<ReviewEntity> findAll() throws SQLException {
        Connection connection = DataBase.getConnection();
        List<ReviewEntity> reviews = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "select * from review"
                )) {
            while (resultSet.next()) {
                reviews.add(new ReviewEntity(
                        resultSet.getLong(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4),
                        resultSet.getLong(5),
                        resultSet.getLong(6)));
            }
            connection.close();
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            connection.close();
            return null;
        }
    }


    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from review where id = ?")) {
            statement.setLong(1, aLong);
            var rs = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }

    public List<ReviewEntity> findByBookId(Long aLong) throws SQLException{
        Connection connection = DataBase.getConnection();
        List<ReviewEntity> reviews = new ArrayList<>();
        try( PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from review where book_id = ?")){
            preparedStatement.setLong(1,aLong);
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reviews.add(new ReviewEntity(
                        rs.getLong(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getLong(5),
                        rs.getLong(6)));
            }
            connection.close();
            return reviews;
        }catch (SQLException e){
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

    public ReviewEntity create(ReviewEntity review) throws SQLException {
        Connection connection = DataBase.getConnection();
        review.setCreatedat(new Timestamp(System.currentTimeMillis()));
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO review (rating, description, createdat, user_id, book_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, review.getRating());
            preparedStatement.setString(2, review.getDescription());
            preparedStatement.setTimestamp(3, review.getCreatedat());
            preparedStatement.setLong(4, review.getUserId());
            preparedStatement.setLong(5, review.getBookId());
            preparedStatement.executeUpdate();
            connection.close();
            return review;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }
    public ReviewEntity updateById(Long id, ReviewEntity entity)throws SQLException{
        if(entity.getRating()==null || entity.getDescription() == null) {
            throw new IllegalArgumentException("The review is null");
        }
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "update review set description = ?, rating = ? where id = ?"
        )){
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getRating());
            statement.setLong(3, id);
            statement.executeUpdate();
            connection.close();
            return findByID(id);
        }catch (SQLException e){
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

}
