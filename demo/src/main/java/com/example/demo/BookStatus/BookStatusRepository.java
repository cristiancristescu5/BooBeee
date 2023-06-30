package com.example.demo.BookStatus;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookStatusRepository {

    public BookStatusEntity findByBookId(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_status where book_id = ?")){
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();

            return rs.next() ?  new BookStatusEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getString(4)) : null;
        }catch (SQLException e){
            e.printStackTrace();

            return null;
        }
    }
    public List<BookStatusEntity> findByUserId(Long aLong) throws SQLException{
        List<BookStatusEntity> bookStatusEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_status where user_id = ?"
        )){
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while(rs.next()){
                bookStatusEntities.add(
                        new BookStatusEntity(rs.getLong(1), rs.getLong(2),
                                rs.getLong(3), rs.getString(4))
                );
            }

            return bookStatusEntities;
        }catch (SQLException e){
            e.printStackTrace();

            return null;
        }

    }
    public List<BookStatusEntity> findByUserIdAndStatus(String status, Long aLong) throws SQLException{
        List<BookStatusEntity> bookStatusEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_status where user_id = ? and status = ?"
        )){
            statement.setLong(1, aLong);
            statement.setString(2, status);
            var rs = statement.executeQuery();
            while(rs.next()){
                bookStatusEntities.add(new BookStatusEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getString(4)));
            }
            return bookStatusEntities;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
   }
    public BookStatusEntity findByAllCredentials(Long bookId, Long userId) throws SQLException{
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_status where user_id = ? and book_id = ?"
        )){
            statement.setLong(1, bookId);
            statement.setLong(2, userId);
            var rs = statement.executeQuery();
            return rs.next() ? new BookStatusEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getString(4)) : null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
    public BookStatusEntity updateBookStatus(String status, Long userId, Long bookId) throws SQLException {
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement((
                "update book_status set status = ? where book_id = ? and user_id = ?"
        ))){
            statement.setString(1, status);
            statement.setLong(2, bookId);
            statement.setLong(3, userId);
            statement.executeUpdate();
            return new BookStatusEntity(userId, bookId, status);
        }
    }

    public BookStatusEntity create(BookStatusEntity bookStatus) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO book_status (book_id, user_id, status) VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, bookStatus.getBookId());
            preparedStatement.setLong(2, bookStatus.getUserId());
            preparedStatement.setString(3, bookStatus.getStatus());
            preparedStatement.executeUpdate();

            return bookStatus;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
