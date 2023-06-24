package com.example.demo.Group;

import com.example.demo.GroupMembers.GroupMembersEntity;
import com.example.demo.GroupMembers.GroupMembersService;
import com.example.demo.RequestBodyParser;
import com.example.demo.User.UserEntity;
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
import java.security.spec.ECField;

@WebServlet(urlPatterns = {"/groups", "/groups/*"})
public class GroupController extends HttpServlet {
    private static final GroupService groupService = new GroupService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final GroupMembersService groupMembersService = new GroupMembersService();
    private static final UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        String email = req.getAttribute("email").toString();
        PrintWriter out = resp.getWriter();
        // /api/v1/groups
        if (words.length == 4 && req.getMethod().equals("POST")) {
            GroupEntity groupEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), GroupEntity.class);
            if (groupEntity.getName().split(" ").length != 1) {
                out.println("Invalid group name");
                out.close();
                resp.setStatus(400);
                return;
            }
            boolean found = true;
            try {
                GroupEntity existingGroup = groupService.findByName(groupEntity.getName());
            } catch (NoResultException e) {
                found = false;
            }
            if (!found) {
                Long userId = userService.findByEmail(email).getId();
                groupEntity.setMembersCount(groupEntity.getMembersCount() + 1);
                GroupEntity group = groupService.addGroup(groupEntity);
                groupMembersService.addMember(groupEntity.getId(), userId);
                out.println(objectMapper.writeValueAsString(group));
                out.close();
                return;
            }
        }

        // /api/v1/groups/{groupName}
        if (words.length == 5) {
//            System.out.println("aiiiivi");
            String groupName = words[4];
            String responseBody;
            Long groupId;
            try {
                GroupEntity group = groupService.findByName(groupName);
                groupId = group.getId();
            } catch (NoResultException e) {
                out.println("The group " + groupName + " does not exist");
                out.close();
                System.out.println("pica grup");
                resp.setStatus(400);
                return;
            }
            Long userId;
            try {
                System.out.println("verific user");
                UserEntity user = userService.findByEmail(email);
                userId = user.getId();
            } catch (NoResultException e) {
                System.out.println("nu exista user");
                out.println("User with email " + email + " does not exist");
                out.close();
                resp.setStatus(400);
                return;
            }
            try {
                System.out.println("verific user in grup");
                groupMembersService.findMemberInAGroup(userId, groupId);//problema

                resp.setStatus(400);
                out.println("You are already in this group");
                out.close();
                return;
            } catch (NoResultException e) {
                System.out.println("nu exista user in grup");
                GroupMembersEntity groupMembersEntity = groupMembersService.addMember(groupId, userId);
                responseBody = objectMapper.writeValueAsString(groupMembersEntity);
                resp.setStatus(201);
                out.println(responseBody);
                out.close();
                return;
            }

        }
        resp.setStatus(400);
        out.println("Bad Request");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // /groups/{groupName}/groupMembers
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        resp.setContentType("application/JSON");
        if (words.length == 6) {
            String groupName = words[4];
            String responseBody;
            try {
                responseBody = objectMapper.writeValueAsString(groupMembersService.findGroupMembers(groupName));
            } catch (Exception e) {
                out.println("Bad request");
                out.close();
                resp.setStatus(400);
                return;
            }
            out.println(responseBody);
            out.close();
            return;
        }
        out.println("Bad request");
        out.close();
        resp.setStatus(400);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    // /groups/{groupName}/groupMembers
    // _ api v1 groups groupName
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("test/plain");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        String email = req.getAttribute("email").toString();
        var words = req.getRequestURI().split("/");
        String groupName = words[4];
        UserEntity entity = userService.findByEmail(email);
        GroupMembersEntity groupMembersEntity;
        GroupEntity groupEntity;
        try {
            groupEntity = groupService.findByName(groupName);
            groupMembersEntity = groupMembersService.findMemberInAGroup(entity.getId(), groupEntity.getId());
            groupMembersService.deleteMember(groupMembersEntity.getId());
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            out.println("You are not in this group");
            out.close();
            resp.setStatus(400);
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println("Bad request");
            out.close();
            resp.setStatus(400);
            return;
        }
        out.println("You left the group");
        out.close();
        resp.setStatus(200);
    }

}
