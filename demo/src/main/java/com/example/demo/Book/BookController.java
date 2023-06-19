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

@WebServlet(urlPatterns = {"/books", "/books/*"})
public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var out = resp.getWriter();
        if (req.getRequestURI().split("/").length == 4 && req.getMethod().equals("POST")) {
            BufferedReader bufferedReader = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
            }
            bufferedReader.close();
            var bookEntity = objectMapper.readValue(body.toString(), BookEntity.class);
            var book = bookService.addBook(bookEntity);
            PrintWriter printWriter = resp.getWriter();
            String responseBody = objectMapper.writeValueAsString(book);
            printWriter.println(responseBody);
            printWriter.close();
        } else {
            resp.setStatus(400);
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        var out = resp.getWriter();
        if (req.getRequestURI().split("/").length == 4 && req.getMethod().equals("GET")) {
            var books = bookService.getAllBooks();
            String responseBody = objectMapper.writeValueAsString(books);
            out.println(responseBody);
            out.close();
        } else if (req.getRequestURI().split("/").length == 5 && req.getMethod().equals("GET")) {
            String id = req.getRequestURI().split("/")[4];
            Long bookId = Long.parseLong(id);
            boolean ok = true;
            String bookString = new String();
            try {
                var book = bookService.getBookById(bookId);
                bookString = objectMapper.writeValueAsString(book);
            } catch (IllegalArgumentException e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                ok = false;
            }
            if (ok) {
                out.println(bookString);
                out.close();
            }
        } else {
            resp.setStatus(400);
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().split("/").length == 5 && req.getMethod().equals("DELETE")) {
            resp.setStatus(200);
            resp.setContentType("application/JSON");
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long bookId = Long.parseLong(id);
            bookService.deleteBook(bookId);
            out.println("Book deleted");
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().split("/").length == 5 && req.getMethod().equals("PUT")) {
            resp.setStatus(200);
            resp.setContentType("application/JSON");
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long bookId = Long.parseLong(id);
            BookEntity bookEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), BookEntity.class);
            BookEntity book = bookService.updateBook(bookId, bookEntity);
            String responseBody = objectMapper.writeValueAsString(book);
            out.println(responseBody);
            out.close();
        } else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }
}
