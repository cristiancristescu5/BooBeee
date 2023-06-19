package com.example.demo.Author;

import java.util.List;

public class AuthorService {
    private final AuthorRepository authorRepository = new AuthorRepository();
    public AuthorEntity addAuthor(AuthorEntity authorEntity){
        return authorRepository.save(authorEntity);
    }
    public List<AuthorEntity> getAllAuthors(){
        return authorRepository.findAll();
    }
    public AuthorEntity getAuthorById(Long id){
        var author = authorRepository.findByID(id);
        if(author == null){
            throw new IllegalArgumentException("Author does not exist in database");
        }else{
            return author;
        }
    }
    public AuthorEntity updateAuthor(Long id, AuthorEntity authorEntity){
        AuthorEntity author = authorRepository.findByID(id);
        if(author == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        author = authorEntity;
        return authorRepository.updateById(id, author);
    }
    public void deleteAuthorById(Long id){
        authorRepository.deleteByID(id);
    }
}
