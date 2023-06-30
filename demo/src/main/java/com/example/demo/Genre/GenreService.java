package com.example.demo.Genre;

import java.sql.SQLException;
import java.util.List;

public class GenreService {
    private static GenreRepository genreRepository = new GenreRepository();

    public GenreEntity addGenre(GenreEntity genreEntity) throws SQLException {
        return genreRepository.create(genreEntity);
    }
    public GenreEntity getGenreById(Long id) throws SQLException{
        var genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Genre does not exist in database");
        }else{
            return genre;
        }
    }
    public GenreEntity updateGenre(Long id, GenreEntity genreEntity) throws SQLException{
        GenreEntity genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        genre = genreEntity;
        return genreRepository.updateById(id, genre);
    }
    public void deleteGenre(Long id) throws SQLException {
        GenreEntity genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        genreRepository.deleteByID(id);
    }
    public List<GenreEntity> getAllGenres()throws SQLException{
        return genreRepository.findAll();
    }
}
