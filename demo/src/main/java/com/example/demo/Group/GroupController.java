package com.example.demo.Group;

import com.example.demo.GroupMembers.GroupMembersService;
import com.example.demo.RequestBodyParser;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/groups", "/groups/*"})
public class GroupController extends HttpServlet {
    private static final GroupService groupService = new GroupService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final GroupMembersService groupMembersService = new GroupMembersService();
    private static final UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        String email = req.getAttribute("email").toString();
        PrintWriter out = resp.getWriter();
        // /api/v1/groups
        if(words.length == 4 && req.getMethod().equals("POST")){
            GroupEntity groupEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), GroupEntity.class);
            boolean found = true;
            try {
                GroupEntity existingGroup = groupService.findByName(groupEntity.getName());
            }catch (NoResultException e){
                found = false;
            }
            if(!found) {
                Long userId = userService.findByEmail(email).getId();
                groupEntity.setMembersCount(groupEntity.getMembersCount() + 1);
                GroupEntity group = groupService.addGroup(groupEntity);
                groupMembersService.addMember(groupEntity.getId(), userId);
                out.println(objectMapper.writeValueAsString(group));
                out.close();
                return;
            }
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
