package com.example.demo.BookAuthors;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookAuthorsRepository {

    public List<BookAuthorsEntity> findByAuthorId(Long aLong) throws SQLException {
        List<BookAuthorsEntity> bookAuthorsEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_authors where author_id = ?"
        )){
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while(rs.next()){
                bookAuthorsEntities.add(new BookAuthorsEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)));
            }
            return bookAuthorsEntities;
        }
    }
    public List<BookAuthorsEntity> findByBookId(Long aLong)throws SQLException {
        List<BookAuthorsEntity> bookAuthorsEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(
                "select * from book_authors where book_id = ?"
        )){
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while(rs.next()){
                bookAuthorsEntities.add(new BookAuthorsEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3)));
            }
            return bookAuthorsEntities;
        }
    }

    public BookAuthorsEntity create(BookAuthorsEntity bookAuthors) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, bookAuthors.getBookId());
            preparedStatement.setLong(2, bookAuthors.getAuthorId());
            preparedStatement.executeUpdate();

            return bookAuthors;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
