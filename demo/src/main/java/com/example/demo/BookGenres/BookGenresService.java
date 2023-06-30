package com.example.demo.BookGenres;

import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookRepository;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.Genre.GenreRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenresService {
    private static final BookGenresRepository bookGenresRepository = new BookGenresRepository();
    private static final GenreRepository genreRepository = new GenreRepository();
    private static final BookRepository bookRepository = new BookRepository();

    public GenreEntity findGenreByBookId(Long bookId) throws SQLException {
        BookGenresEntity bookGenresEntity = bookGenresRepository.findByBookId(bookId);
        GenreEntity genre = genreRepository.findByID(bookGenresEntity.getGenreId());
        return genre;
    }

    public List<BookEntity> findByGenre(String genre) throws SQLException {
        Long genreId = genreRepository.findByName(genre).getId();
        List<BookGenresEntity> bookGenresEntities = bookGenresRepository.findByGenreId(genreId);
        List<BookEntity> books = new ArrayList<>();
        for(BookGenresEntity b: bookGenresEntities){
            books.add(bookRepository.findByID(b.getBookId()));
        }
        return books;
    }
}
