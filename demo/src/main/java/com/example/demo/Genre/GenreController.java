package com.example.demo.Genre;

import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = {"/genres", "/genres/*"})
public class GenreController extends HttpServlet{
    private final GenreService genreService = new GenreService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(201);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if(req.getRequestURI().split("/").length==4 && req.getMethod().equals("POST")){
            GenreEntity genreEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), GenreEntity.class);
            GenreEntity genre = genreService.addGenre(genreEntity);
            String responseBody = objectMapper.writeValueAsString(genre);
            out.println(responseBody);
            out.close();
        }else {
            resp.setStatus(400);
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if (req.getRequestURI().split("/").length == 4) {
            var genres = genreService.getAllGenres();
            String responseBody = objectMapper.writeValueAsString(genres);
            out.println(responseBody);
            out.close();
            return;
        }
        if(req.getRequestURI().split("/").length==5){
            String id = req.getRequestURI().split("/")[4];
            Long genreId = Long.parseLong(id);
            boolean ok = true;
            String genreString;
            try{
                GenreEntity genre = genreService.getGenreById(genreId);
                genreString = objectMapper.writeValueAsString(genre);
            }catch (IllegalArgumentException e){
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            out.println(genreString);
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var words = req.getRequestURI().split("/");
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if(words.length==5){
            String id = words[4];
            Long genreId = Long.parseLong(id);
            GenreEntity genreEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), GenreEntity.class);
            GenreEntity genre = genreService.updateGenre(genreId, genreEntity);
            String responseBody = objectMapper.writeValueAsString(genre);
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
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        if(words.length==5){
            String id = words[4];
            Long genreId = Long.parseLong(id);
            genreService.deleteGenre(genreId);
            out.println("Deleted");
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }
}
