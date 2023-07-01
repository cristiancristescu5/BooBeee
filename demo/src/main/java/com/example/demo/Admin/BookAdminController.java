package com.example.demo.Admin;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.Author.AuthorService;
import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookService;
import com.example.demo.BookAuthors.BookAuthorsEntity;
import com.example.demo.BookAuthors.BookAuthorsService;
import com.example.demo.BookGenres.BookGenresEntity;
import com.example.demo.BookGenres.BookGenresService;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.Genre.GenreService;
import com.example.demo.RequestBodyParser;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/bookAdmin", "/admin"})
public class BookAdminController extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookService bookService = new BookService();
    private final AuthorService authorService = new AuthorService();
    private final GenreService genreService = new GenreService();
    private final BookGenresService bookGenresService = new BookGenresService();
    private final BookAuthorsService bookAuthorsService = new BookAuthorsService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // /api/v1/admin
        var words = req.getRequestURI().split("/");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        String email;
        try {
            email = req.getAttribute("email").toString();
        }catch (NullPointerException e){
            resp.setStatus(401);
            out.println("nu e voie");
            out.close();
            return;
        }
        UserEntity user = null;
        try {
            user = userService.findByEmail(email);
        } catch (Exception e) {
            resp.setStatus(401);
            out.println("nu e voie");
            out.close();
            return;
        }
        if (user == null) {
            resp.setStatus(401);
            out.println("nu e voie");
            out.close();
            return;
        }
        if (!user.isAdmin()) {
            resp.setStatus(401);
            out.println("nu e voie");
            out.close();
            return;
        }
        if (words.length == 4 && words[3].equals("admin")) {
            resp.setStatus(200);
            out.println("Bau");
            out.close();
            return;
        }
        resp.setStatus(401);
        out.println("nu e voie");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        // /api/v1/books -- aduaga carte
        if (words.length == 4 && words[3].equals("bookAdmin")) {
            BookGenreAuthor bookGenreAuthor = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookGenreAuthor.class);
            BookEntity book = new BookEntity(bookGenreAuthor.getTitle(), bookGenreAuthor.getIsbn(), bookGenreAuthor.getDescription(), UUID.randomUUID().toString());
            Long authorId = bookGenreAuthor.getAuthorId();
            String genre = bookGenreAuthor.getGenre();
            GenreEntity genre1 = null;
            AuthorEntity author = null;
            try {
                genre1 = genreService.getGenreByName(genre);
                author = authorService.getAuthorById(authorId);
            } catch (Exception e) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            if (genre1 == null || author == null) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            BookEntity savedBook = null;
            try {
                savedBook = bookService.addBook(book);
            } catch (Exception e) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            if (savedBook == null) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            BookGenresEntity bookGenresEntity = null;
            BookAuthorsEntity bookAuthorsEntity = null;
            try {
                bookGenresEntity = bookGenresService.create(new BookGenresEntity(savedBook.getId(), genre1.getId()));
                bookAuthorsEntity = bookAuthorsService.create(new BookAuthorsEntity(savedBook.getId(), authorId));
            } catch (Exception e) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            if (bookAuthorsEntity == null || bookGenresEntity == null) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            resp.setStatus(201);
            out.println("created");
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }

//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        var words = req.getRequestURI().split("/");
//        resp.setStatus(200);
//        resp.setContentType("application/JSON");
//        PrintWriter out = resp.getWriter();
//        // api/v1/
//        if (words.length == 5 ) {
//            String id = words[4];
//            Long bookId = Long.parseLong(id);
//            try {
//                bookService.deleteBook(bookId);
//            } catch (IllegalArgumentException | SQLException e) {
//                throw new RuntimeException(e);
//            }
//            out.println("Book deleted");
//            out.close();
//            return;
//        }
//        resp.setStatus(400);
//        out.println("Bad request");
//        out.close();
//    }

    // /api/v1/bookAdmin/{bookId}
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setStatus(200);
//        resp.setContentType("application/JSON");
//        PrintWriter out = resp.getWriter();
//        var words = req.getRequestURI().split("/");
//        if (words.length == 5 && req.getMethod().equals("PUT")) {
//            String id = req.getRequestURI().split("/")[4];
//            Long bookId = Long.parseLong(id);
//            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
//            BookEntity book = null;
//            try {
//                book = bookService.updateBook(bookId, bookEntity);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            String responseBody = objectMapper.writeValueAsString(book);
//            out.println(responseBody);
//            out.close();
//            return;
//        }
//        resp.setStatus(400);
//        out.println("Bad request");
//        out.close();
//    }
}
