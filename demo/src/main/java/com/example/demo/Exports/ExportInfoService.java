package com.example.demo.Exports;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.Author.AuthorRepository;
import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookRepository;
import com.example.demo.BookAuthors.BookAuthorsEntity;
import com.example.demo.BookAuthors.BookAuthorsRepository;
import com.example.demo.BookGenres.BookGenresEntity;
import com.example.demo.BookGenres.BookGenresRepository;
import com.example.demo.BookStatus.BookStatusEntity;
import com.example.demo.BookStatus.BookStatusRepository;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.Genre.GenreRepository;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class ExportInfoService {
    private final BookRepository bookRepository = new BookRepository();
    private final BookStatusRepository bookStatusRepository = new BookStatusRepository();
    private final UserRepository userRepository = new UserRepository();
    private final BookAuthorsRepository bookAuthorsRepository = new BookAuthorsRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final BookGenresRepository bookGenresRepository = new BookGenresRepository();
    private final GenreRepository genreRepository = new GenreRepository();
    public List<ExportInfo> getExportInfos(String email){
        List<ExportInfo> exportInfos = new ArrayList<>();
        UserEntity user = userRepository.findByEmail(email);
        List<BookStatusEntity> bookStatusEntities = bookStatusRepository.findByUserId(user.getId());
        for(BookStatusEntity b : bookStatusEntities){
            try {
                BookEntity bookEntity = bookRepository.findByID(b.getBookId());
                BookAuthorsEntity bookAuthorsEntity = bookAuthorsRepository.findByBookId(bookEntity.getId()).get(0);
                AuthorEntity author = authorRepository.findByID(bookAuthorsEntity.getAuthorId());
                BookGenresEntity bookGenresEntity = bookGenresRepository.findByBookId(bookEntity.getId());
                GenreEntity genre = genreRepository.findByID(bookGenresEntity.getGenreId());
                exportInfos.add(new ExportInfo(bookEntity.getTitle(), bookEntity.getDescription(), author.getName(),
                        bookEntity.getIsbn(), genre.getName(), b.getStatus()));
            }catch (NoResultException e){
                System.out.println("n am gasit nimic intr una dintre tabele.");
            }
        }
        return exportInfos;
    }
}
