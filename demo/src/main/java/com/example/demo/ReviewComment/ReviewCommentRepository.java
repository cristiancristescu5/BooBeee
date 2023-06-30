package com.example.demo.ReviewComment;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewCommentRepository {
    public ReviewCommentEntity findByUserId(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from review_comment where user_id = ?"
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            ReviewCommentEntity e = rs.next() ? new ReviewCommentEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getLong(4)) : null;
            connection.close();
            return e;
        }catch(SQLException e){
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

    public List<ReviewCommentEntity> findByReviewId(Long aLong) throws SQLException {
        List<ReviewCommentEntity> reviewCommentEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from review_comment where review_id = ?"
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while (rs.next()) {
                reviewCommentEntities.add(new ReviewCommentEntity(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getLong(4)
                ));
            }
            connection.close();
            return reviewCommentEntities;
        }
    }
    public List<ReviewCommentEntity> findAll() throws SQLException {
        Connection connection = DataBase.getConnection();
        List<ReviewCommentEntity> reviewCommentEntities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from review_comment"
        )) {
            var rs = statement.executeQuery();
            while (rs.next()) {
                reviewCommentEntities.add(new ReviewCommentEntity(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getLong(4)
                ));
            }
            connection.close();
            return reviewCommentEntities;
        }

    }


    public ReviewCommentEntity create(ReviewCommentEntity reviewComment) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO review_comment (user_id, review_id, comment_id) VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, reviewComment.getUserId());
            preparedStatement.setLong(2, reviewComment.getReviewId());
            preparedStatement.setLong(3, reviewComment.getCommentId());
            preparedStatement.executeUpdate();
            connection.close();
            return reviewComment;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }

}
