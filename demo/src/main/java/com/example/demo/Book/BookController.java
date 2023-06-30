package com.example.demo.Book;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.BookAuthors.BookAuthorsService;
import com.example.demo.BookGenres.BookGenresService;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.Review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/books", "/books/*"})
// /book/id, /book/id/reviews, /book/id/reviews/id, /book/id/reviews/id/comments /
public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookAuthorsService bookAuthorsService = new BookAuthorsService();
    private final ReviewService reviewService = new ReviewService();
    private final BookGenresService bookGenresService = new BookGenresService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        System.out.println(Arrays.toString(words));
        // api/v1/books/{bookId}/rating
        if(words.length==6 && words[5].equals("rating")){
            Long bookId = Long.parseLong(words[4]);
            String responseBody;
            float rating = 0.0f;
            try{
                rating = reviewService.getBookRating(bookId);
            } catch (Exception e){
                e.printStackTrace();
                resp.setStatus(400);
                out.println(e.getMessage());
                out.close();
                return;
            }
            responseBody = "{"+"\n"+"\"rating\" : \""+rating+"\""+"\n"+"}";
            resp.setStatus(200);
            out.println(responseBody);
            out.close();
            return;
        }

        //get all books
        if (words.length == 4 && req.getMethod().equals("GET")) {
            List<BookEntity> books = null;
            try {
                books = bookService.getAllBooks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String responseBody = objectMapper.writeValueAsString(books);
            out.println(responseBody);
            out.close();
            return;
        }
        //get only a book, url = /books/{bookId} _ api v1 books id
        if (words.length == 5 && !words[4].split("=")[0].equals("filter") && req.getMethod().equals("GET")) {
            String id = words[4];
            Long bookId = Long.parseLong(id);
            String bookString = new String();
            try {
                BookEntity book = bookService.getBookById(bookId);
                bookString = objectMapper.writeValueAsString(book);
            } catch (IllegalArgumentException | SQLException e) {
                e.printStackTrace();
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            out.println(bookString);
            out.close();
            return;
        }
//        get the author of a book, url = /books/{bookId}/author
        if (words.length == 6 && req.getMethod().equals("GET")) {
            if (words[5].equals("author")) {
                String id = words[4];
                Long bookId = Long.parseLong(id);
                String authorString;
                try {
                    AuthorEntity author = bookAuthorsService.getAuthorByBookId(bookId);
                    authorString = objectMapper.writeValueAsString(author);
                } catch (Exception e) {
                    resp.setStatus(204);
                    out.println(e.getMessage());
                    out.close();
                    return;
                }
                resp.setStatus(200);
                out.println(authorString);
                out.close();
                return;
            }
        }
        // get the genre of a book, url = /books/{bookId}/genre
        if (words.length == 6 && req.getMethod().equals("GET")) {
            if (words[5].equals("genre")) {
                String id = words[4];
                Long bookId = Long.parseLong(id);
                String responseBody;
                try {
                    GenreEntity genre = bookGenresService.findGenreByBookId(bookId);
                    responseBody = objectMapper.writeValueAsString(genre);
                } catch (Exception e) {
                    out.println(e.getMessage());
                    resp.setStatus(204);
                    out.close();
                    return;
                }
                out.println(responseBody);
                out.close();
                resp.setStatus(200);
                return;
            }
        }

        // /api/v1/books/filter={genre name} filtrarea dupa gen
        if (words.length == 5 && req.getMethod().equals("GET")) {
            if (words[4].split("=")[0].equals("filter")) {
                String genre = words[4].split("=")[1];
                String responseBody;
                try {
                    List<BookEntity> books = bookGenresService.findByGenre(genre);
                    responseBody = objectMapper.writeValueAsString(books);
                } catch (Exception e) {
                    out.println(e.getMessage());
                    resp.setStatus(204);
                    out.close();
                    return;
                }
                out.println(responseBody);
                out.close();
                resp.setStatus(200);
                return;
            }
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();

    }
}
