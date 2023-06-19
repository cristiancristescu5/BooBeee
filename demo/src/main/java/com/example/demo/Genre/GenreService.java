package com.example.demo.Genre;

import java.util.List;

public class GenreService {
    private static GenreRepository genreRepository = new GenreRepository();
    public GenreEntity addGenre(GenreEntity genreEntity){
        return genreRepository.save(genreEntity);
    }
    public GenreEntity getGenreById(Long id){
        var genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Genre does not exist in database");
        }else{
            return genre;
        }
    }
    public GenreEntity updateGenre(Long id, GenreEntity genreEntity){
        GenreEntity genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        genre = genreEntity;
        return genreRepository.updateById(id, genre);
    }
    public void deleteGenre(Long id){
        GenreEntity genre = genreRepository.findByID(id);
        if(genre == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        genreRepository.deleteByID(id);
    }
    public List<GenreEntity> getAllGenres(){
        return genreRepository.findAll();
    }
}
