package com.example.demo.BookGenres;

import com.example.demo.Genre.GenreEntity;
import com.example.demo.Genre.GenreRepository;

public class BookGenresService {
    private static final BookGenresRepository bookGenresRepository = new BookGenresRepository();
    private static final GenreRepository genreRepository = new GenreRepository();
    public GenreEntity findGenreByBookId(Long bookId){
        BookGenresEntity bookGenresEntity = bookGenresRepository.findByBookId(bookId);
        GenreEntity genre = genreRepository.findByID(bookGenresEntity.getGenreId());
        return genre;
    }
}
