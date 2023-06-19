package com.example.demo.Comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/comments", "/comments/*"})
public class CommentController extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("GET")){
            String id = req.getRequestURI().split("/")[4];
            Long commentId = Long.parseLong(id);
            boolean ok = true;
            String commentString = new String();
            try{
                CommentEntity comment = commentService.getCommentById(commentId);
                commentString = objectMapper.writeValueAsString(comment);
            }catch (IllegalArgumentException e){
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                ok = false;
            }
            if(ok){
                out.println(commentString);
                out.close();
            }
        } else if (req.getRequestURI().split("/").length==4 && req.getMethod().equals("GET")){
            var commentEntityList = commentService.getAllComments();
            var responseBody = objectMapper.writeValueAsString(commentEntityList);
            out.println(responseBody);
            out.close();
        } else {
            resp.setStatus(400);
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        if(req.getRequestURI().split("/").length==4 & req.getMethod().equals("POST")){
            BufferedReader bufferedReader = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!=null){
                body.append(line);
            }
            bufferedReader.close();
            var commentEntity = objectMapper.readValue(body.toString(), CommentEntity.class);
            var comment = commentService.addComment(commentEntity);
            resp.setStatus(201);
            var out = resp.getWriter();
            out.println(objectMapper.writeValueAsString(comment));
            out.close();
        } else {
            resp.setStatus(400);
            var out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("PUT")){
            resp.setStatus(200);
            resp.setContentType("application/JSON");
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long commentId = Long.parseLong(id);
            BufferedReader bufferedReader = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!=null){
                body.append(line);
            }
            bufferedReader.close();
            CommentEntity commentEntity = objectMapper.readValue(body.toString(), CommentEntity.class);
            CommentEntity comment = commentService.updateComment(commentId, commentEntity);
            String commentString = objectMapper.writeValueAsString(comment);
            out.println(commentString);
            out.close();
        }else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("DELETE")){
            resp.setStatus(200);
            resp.setContentType("application/JSON");
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long commentId = Long.parseLong(id);
            commentService.deleteComment(commentId);
            out.println("Comment deleted");
            out.close();
        }else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }
}
