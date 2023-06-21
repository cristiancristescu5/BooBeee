package com.example.demo.Book;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.BookAuthors.BookAuthorsService;
import com.example.demo.BookGenres.BookGenresService;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.RequestBodyParser;
import com.example.demo.Review.ReviewEntity;
import com.example.demo.Review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
//@Path("/books")
@WebServlet(urlPatterns = {"/books", "/books/*"})// /book/id, /book/id/reviews, /book/id/reviews/id, /book/id/reviews/id/comments /
public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookAuthorsService bookAuthorsService = new BookAuthorsService();
    private final ReviewService reviewService = new ReviewService();
    private final BookGenresService bookGenresService = new BookGenresService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        if (words.length == 4 && req.getMethod().equals("POST")) {
            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
            BookEntity book = bookService.addBook(bookEntity);
            String responseBody = objectMapper.writeValueAsString(book);
            out.println(responseBody);
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        System.out.println(Arrays.toString(words));
        //get all books
        if (words.length == 4&& req.getMethod().equals("GET")) {
            List<BookEntity> books = bookService.getAllBooks();
            String responseBody = objectMapper.writeValueAsString(books);
            out.println(responseBody);
            out.close();
            return;
        }
        //get only a book, url = /books/{bookId}
        if (words.length == 5 && req.getMethod().equals("GET")) {
            String id = words[4];
            Long bookId = Long.parseLong(id);
            String bookString = new String();
            try {
                BookEntity book = bookService.getBookById(bookId);
                bookString = objectMapper.writeValueAsString(book);
            } catch (IllegalArgumentException e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            out.println(bookString);
            out.close();
            return;
        }
        //get the author of a book, url = /books/{bookId}/author
        if(words.length == 6 && req.getMethod().equals("GET")){
            if(words[5].equals("author")){
                String id = words[4];
                Long bookId = Long.parseLong(id);
                String authorString;
                try{
                    AuthorEntity author = bookAuthorsService.getAuthorByBookId(bookId);
                    authorString = objectMapper.writeValueAsString(author);
                }catch (Exception e){
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
        //get the reviews of a book, url = /books/{bookId}/reviews
        if(words.length == 6 && req.getMethod().equals("GET")){
            if(words[5].equals("reviews")){
                String id = words[4];
                Long bookId = Long.parseLong(id);
                String responseBody;
                try{
                    List<ReviewEntity> reviewEntities = reviewService.findReviewsByBookId(bookId);
                    responseBody = objectMapper.writeValueAsString(reviewEntities);
                }catch(Exception e){
                    resp.setStatus(204);
                    out.println(e.getMessage());
                    out.close();
                    return;
                }
                resp.setStatus(200);
                out.println(responseBody);
                out.close();
                return;
            }
        }
        if(words.length == 6 && req.getMethod().equals("GET")){
            if(words[5].equals("genre")){
                String id = words[4];
                Long bookId = Long.parseLong(id);
                String responseBody;
                try{
                    GenreEntity genre = bookGenresService.findGenreByBookId(bookId);
                    responseBody = objectMapper.writeValueAsString(genre);
                }catch(Exception e){
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var words = req.getRequestURI().split("/");
        resp.setStatus(400);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if (words.length == 5 && req.getMethod().equals("DELETE")) {
            String id = words[4];
            Long bookId = Long.parseLong(id);
            bookService.deleteBook(bookId);
            out.println("Book deleted");
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 5 && req.getMethod().equals("PUT")) {
            String id = req.getRequestURI().split("/")[4];
            Long bookId = Long.parseLong(id);
            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
            BookEntity book = bookService.updateBook(bookId, bookEntity);
            String responseBody = objectMapper.writeValueAsString(book);
            out.println(responseBody);
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }
}
