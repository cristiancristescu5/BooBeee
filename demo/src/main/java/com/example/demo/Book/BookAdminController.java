package com.example.demo.Book;

import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

@WebServlet(urlPatterns = "/bookAdmin")
public class BookAdminController extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookService bookService = new BookService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        // /api/v1/books -- aduaga carte
        if (words.length == 4 && req.getMethod().equals("POST")) {
            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
            BookEntity book = null;
            try {
                book = bookService.addBook(bookEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var words = req.getRequestURI().split("/");
        resp.setStatus(400);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if (words.length == 5 && req.getMethod().equals("DELETE")) {
            String id = words[4];
            Long bookId = Long.parseLong(id);
            try {
                bookService.deleteBook(bookId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            BookEntity book = null;
            try {
                book = bookService.updateBook(bookId, bookEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
