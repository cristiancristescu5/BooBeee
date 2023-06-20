package com.example.demo.Comment;

import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/comments", "/comments/*"})
public class CommentController extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 5 && req.getMethod().equals("GET")) {
            String id = words[4];
            Long commentId = Long.parseLong(id);
            String commentString = new String();
            try {
                CommentEntity comment = commentService.getCommentById(commentId);
                commentString = objectMapper.writeValueAsString(comment);
            } catch (IllegalArgumentException e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            out.println(commentString);
            out.close();
            return;
        }
        if (req.getRequestURI().split("/").length == 4 && req.getMethod().equals("GET")) {
            List<CommentEntity> commentEntityList = commentService.getAllComments();
            String responseBody = objectMapper.writeValueAsString(commentEntityList);
            out.println(responseBody);
            out.close();
            return;
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
        if (words.length == 4 & req.getMethod().equals("POST")) {
            CommentEntity commentEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), CommentEntity.class);
            CommentEntity comment = commentService.addComment(commentEntity);
            resp.setStatus(201);
            out.println(objectMapper.writeValueAsString(comment));
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
            String id = words[4];
            Long commentId = Long.parseLong(id);
            CommentEntity commentEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), CommentEntity.class);
            CommentEntity comment = commentService.updateComment(commentId, commentEntity);
            String commentString = objectMapper.writeValueAsString(comment);
            out.println(commentString);
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        if (words.length == 5 && req.getMethod().equals("DELETE")) {
            String id = words[4];
            Long commentId = Long.parseLong(id);
            commentService.deleteComment(commentId);
            out.println("Comment deleted");
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }
}
