package com.example.demo.Book;

import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/books", "/books/*"})

public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 4 && req.getMethod().equals("GET")) {
            List<BookEntity> books = bookService.getAllBooks();
            String responseBody = objectMapper.writeValueAsString(books);
            out.println(responseBody);
            out.close();
            return;
        }
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
