package com.example.demo.Book;

import com.example.demo.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookRepository {
    public BookEntity findByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from book where id = ?"
        )) {
            statement.setLong(1, aLong);
            var resultSet = statement.executeQuery();
            BookEntity book = resultSet.next() ? new BookEntity(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)) : null;
            connection.close();
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }


    public List<BookEntity> findAll() throws SQLException {
        Connection connection = DataBase.getConnection();
        List<BookEntity> books = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "select * from book"
                )) {
            while (resultSet.next()) {
                books.add(new BookEntity(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
            connection.close();
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            connection.close();
            return null;
        }
    }

    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        PreparedStatement statement = null;
        try {
            String sql = "DELETE FROM book WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
//            assert connection != null;
            connection.close();
        }
    }

    public BookEntity updateById(Long id, BookEntity bookEntity) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update book set title = ?, isbn = ?, description = ?, picture = ? where id = ?")) {
            preparedStatement.setString(1, bookEntity.getTitle());
            preparedStatement.setString(2, bookEntity.getIsbn());
            preparedStatement.setString(3, bookEntity.getDescription());
            preparedStatement.setString(4, bookEntity.getPicture());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            connection.close();
            return bookEntity;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            connection.close();
            return null;
        }
    }

    public BookEntity create(BookEntity book) throws SQLException {
        Connection connection = DataBase.getConnection();
        book.setPicture(UUID.randomUUID().toString());
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into book (title, isbn, description, picture) values (?, ?, ?, ?)")){
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setString(4, book.getPicture());
            preparedStatement.executeUpdate();
            connection.close();
            return findMaxID();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
            return null;
        }
    }
    public BookEntity findMaxID()throws SQLException{
        Connection connection = DataBase.getConnection();
        Long id;
        try(PreparedStatement statement = connection.prepareStatement(
                "select max(id) from book"
        )) {
            var rs = statement.executeQuery();
            id = rs.next() ? rs.getLong(1) : null;
        }
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book where id = ?"
        )) {
            statement.setLong(1, id);
            var rs = statement.executeQuery();
            BookEntity book = rs.next() ? new BookEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
            ) : null;
            connection.close();
            return book;
        }
    }
}
