package com.example.demo.Group;

import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/groups", "/groups/*"})
public class GroupController extends HttpServlet {
    private static final GroupService groupService = new GroupService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        if(words.length == 4 && req.getMethod().equals("POST")){
            GroupEntity groupEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), GroupEntity.class);
            GroupEntity group = groupService.addGroup(groupEntity);
            out.println(objectMapper.writeValueAsString(group));
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("BadRequest");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
