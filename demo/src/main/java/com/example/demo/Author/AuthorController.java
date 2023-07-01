package com.example.demo.Author;

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
import java.util.List;

@WebServlet(urlPatterns = {"/authors", "/authors/*"})
public class AuthorController extends HttpServlet {
    private final AuthorService authorService = new AuthorService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();

        var words = req.getRequestURI().split("/");
        if (words.length == 5) {
            String id = words[4];
            Long authorId = Long.parseLong(id);
            String authorString;
            try {
                AuthorEntity author = authorService.getAuthorById(authorId);
                authorString = objectMapper.writeValueAsString(author);
            } catch (IllegalArgumentException | SQLException e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            out.println(authorString);
            out.close();
            return;
        }
        if (req.getRequestURI().split("/").length == 4) {
            try {
                List<AuthorEntity> authorEntityList = authorService.getAllAuthors();
                String responseBody = objectMapper.writeValueAsString(authorEntityList);
                out.println(responseBody);
                out.close();
                return;
            } catch (SQLException e) {
                resp.setStatus(400);
                out.println("Bad request");
                out.close();
                return;
            }
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
            String id = words[4];
            Long authorId = Long.parseLong(id);
            AuthorEntity author = objectMapper.readValue(RequestBodyParser.parseRequest(req), AuthorEntity.class);
            try {
                AuthorEntity authorEntity = authorService.updateAuthor(authorId, author);
                String responseBody = objectMapper.writeValueAsString(authorEntity);
                out.println(responseBody);
                out.close();
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(400);
                out.println("Bad request");
                out.close();
                return;
            }
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 4 && req.getMethod().equals("POST")) {
            AuthorEntity author = objectMapper.readValue(RequestBodyParser.parseRequest(req), AuthorEntity.class);
            AuthorEntity authorEntity = null;
            try {
                authorEntity = authorService.addAuthor(author);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String responseBody = objectMapper.writeValueAsString(authorEntity);
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
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 5 && req.getMethod().equals("DELETE")) {
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            String id = req.getRequestURI().split("/")[4];
            Long authorId = Long.parseLong(id);
            try {
                authorService.deleteAuthorById(authorId);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(400);
                out.println("Bad request");
                out.close();
                return;
            }
            out.println("Author with id: " + authorId + " was deleted");
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }
}
