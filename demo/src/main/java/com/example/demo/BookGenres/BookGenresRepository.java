package com.example.demo.BookGenres;

import com.example.demo.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenresRepository {

    public List<BookGenresEntity> findByGenreId(Long aLong) throws SQLException {
        List<BookGenresEntity> bookGenresEntities = new ArrayList<>();
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from book_genres where genre_id = ?"
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            while (rs.next()) {
                bookGenresEntities.add(
                        new BookGenresEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3))
                );
            }
            return bookGenresEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BookGenresEntity findByBookId(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from book_genres where book_id = ?"
        )) {
            statement.setLong(1, aLong);
            var rs = statement.executeQuery();
            return rs.next() ?
                    new BookGenresEntity(rs.getLong(1), rs.getLong(2), rs.getLong(3))
                    : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void deleteByID(Long aLong) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from book_genres where id = ?")) {
            statement.setLong(1, aLong);
            var rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookGenresEntity create(BookGenresEntity bookGenres) throws SQLException {
        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO book_genres (book_id, genre_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, bookGenres.getBookId());
            preparedStatement.setLong(2, bookGenres.getGenreId());
            preparedStatement.executeUpdate();
            connection.close();
            return bookGenres;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
