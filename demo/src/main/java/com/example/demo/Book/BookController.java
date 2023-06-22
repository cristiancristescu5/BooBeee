package com.example.demo.Book;

import com.example.demo.Author.AuthorEntity;
import com.example.demo.BookAuthors.BookAuthorsService;
import com.example.demo.BookGenres.BookGenresService;
import com.example.demo.Comment.CommentEntity;
import com.example.demo.Genre.GenreEntity;
import com.example.demo.RequestBodyParser;
import com.example.demo.Review.ReviewEntity;
import com.example.demo.Review.ReviewService;
import com.example.demo.ReviewComment.ReviewCommentService;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
@WebServlet(urlPatterns = {"/books", "/books/*"})// /book/id, /book/id/reviews, /book/id/reviews/id, /book/id/reviews/id/comments /
public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookAuthorsService bookAuthorsService = new BookAuthorsService();
    private final ReviewService reviewService = new ReviewService();
    private final BookGenresService bookGenresService = new BookGenresService();
    private final ReviewCommentService reviewCommentService = new ReviewCommentService();
    private final UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        // /api/v1/books -- aduaga carte
        if (words.length == 4 && req.getMethod().equals("POST")) {
            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
            BookEntity book = bookService.addBook(bookEntity);
            String responseBody = objectMapper.writeValueAsString(book);
            out.println(responseBody);
            out.close();
            return;
        }
        // /api/v1/books/{bookId}/reviews -- adugarea unui review

        //{
        //  "description":
        //  "rating":
        //}
        if(words.length==6 && words[5].equals("reviews")){
            String responseBody;
            try {
                String bookId = words[4];
                Long bId = Long.parseLong(bookId);
                Object email = req.getAttribute("email");
                String userEmail = email.toString();
                Long userId = userService.findByEmail(userEmail).getId();
                ReviewEntity reviewEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), ReviewEntity.class);
                reviewEntity.setBookId(bId);
                reviewEntity.setUserId(userId);
                ReviewEntity review = reviewService.create(reviewEntity);
                responseBody = objectMapper.writeValueAsString(review);
            }catch (Exception e){
                out.println(e.getMessage());
                out.close();
                resp.setStatus(400);
                return;
            }
            out.println(responseBody);
            out.close();
            return;

        }

        // /api/v1/books/{booksId}/reviews/{reviewId} -- adaugarea unui comentariu

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
        if (words.length == 5 && !words[4].split("=")[0].equals("filter") && req.getMethod().equals("GET")) {
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
        // get the genre of a book, url = /books/{bookId}/genre
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
        //get the comments of a review, url = /books/{bookId}/reviews/{reviewId}/comments
        if(words.length == 8 && req.getMethod().equals("GET")){
            if(words[7].equals("comments")){
                String id1 = words[4];
                Long bookId = Long.parseLong(id1);
                String id2 = words[6];
                Long reviewId = Long.parseLong(id2);
                String responseBody;
                try{
                    List<CommentEntity> commentEntities = reviewCommentService.findCommentsByReviewId(reviewId);
                    responseBody = objectMapper.writeValueAsString(commentEntities);
                }catch (Exception e){
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
        if(words.length == 5 && req.getMethod().equals("GET")){
            if(words[4].split("=")[0].equals("filter")){
                String genre = words[4].split("=")[1];
                String responseBody;
                try{
                    List<BookEntity> books = bookGenresService.findByGenre(genre);
                    responseBody = objectMapper.writeValueAsString(books);
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
