package com.example.demo.Author;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/authors", "/authors/*"})
public class AuthorController extends HttpServlet {
    private final AuthorService authorService = new AuthorService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Faker faker = new Faker();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        var out = resp.getWriter();
        if (req.getRequestURI().split("/").length == 5) {
            String id = req.getRequestURI().split("/")[4];
            System.out.println(id);
            Long authorId = Long.parseLong(id);
            boolean ok = true;
            System.out.println(authorId);
            var authorString = new String();
            try {
                var author = authorService.getAuthorById(authorId);
                authorString = objectMapper.writeValueAsString(author);
            } catch (IllegalArgumentException e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                ok = false;
            }
            if (ok) {
                out.println(authorString);
                out.close();
            }
        } else if (req.getRequestURI().split("/").length == 4) {
            var authorEntityList = authorService.getAllAuthors();
            var responseBody = objectMapper.writeValueAsString(authorEntityList);
            out.println(responseBody);
            out.close();
        } else {
            resp.setStatus(400);
            out.println("Bad request");
            out.close();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("PUT")){
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long authorId = Long.parseLong(id);
            BufferedReader bufferedReader = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!=null){
                body.append(line);
            }
            bufferedReader.close();
            AuthorEntity author = objectMapper.readValue(body.toString(), AuthorEntity.class);
            AuthorEntity authorEntity = authorService.updateAuthor(authorId, author);
            String responseBody = objectMapper.writeValueAsString(authorEntity);
            out.println(responseBody);
            out.close();
        }else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().split("/").length == 4 && req.getMethod().equals("POST")) {
            resp.setContentType("application/JSON");
            resp.setStatus(201);
            BufferedReader bufferedReader = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
            }
            bufferedReader.close();
            AuthorEntity author = objectMapper.readValue(body.toString(), AuthorEntity.class);
            AuthorEntity authorEntity = authorService.addAuthor(author);
            PrintWriter out = resp.getWriter();
            String responseBody = objectMapper.writeValueAsString(authorEntity);
            out.println(responseBody);
            out.close();
        }else{
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("DELETE")){
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long authorId = Long.parseLong(id);
            authorService.deleteAuthorById(authorId);
            out.println("Author with id: "+authorId+" was deleted");
            out.close();
        }else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }
}
