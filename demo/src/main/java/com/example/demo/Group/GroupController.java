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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

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
            GroupEntity existingGroup = null;
            try {
                existingGroup = groupService.findByName(groupEntity.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (existingGroup == null) {
                found = false;
            }
            if (!found) {
                Long userId = null;
                try {
                    userId = userService.findByEmail(email).getId();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                groupEntity.setMembersCount(groupEntity.getMembersCount() + 1);
                GroupEntity group = null;
                try {
                    group = groupService.addGroup(groupEntity);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    groupMembersService.addMember(groupEntity.getId(), userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.setStatus(400);
                    out.println("Bad Request");
                    out.close();
                    return;
                }
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
            GroupEntity group;
            try {
                group = groupService.findByName(groupName);
                groupId = group.getId();
            } catch (SQLException e) {
                resp.setStatus(400);
                out.println(e.getMessage());
                out.close();
                System.out.println("pica grup");
                return;
            }
            if (group == null) {
                resp.setStatus(400);
                out.println("The group does not exist");
                out.close();
                System.out.println("pica grup");
                return;
            }
            Long userId;
            UserEntity user;
            try {
                System.out.println("verific user");
                user = userService.findByEmail(email);
                userId = user.getId();
            } catch (SQLException e) {
                System.out.println("nu exista user");
                out.println(e.getMessage());
                out.close();
                resp.setStatus(400);
                return;
            }
            if (user == null) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }

            System.out.println("verific user in grup");
            GroupMembersEntity groupMembersEntity1;
            try {
                groupMembersEntity1 = groupMembersService.findMemberInAGroup(userId, groupId);//problema
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (groupMembersEntity1 != null) {
                resp.setStatus(400);
                out.println("You are already in this group");
                out.close();
                return;
            }

            System.out.println("nu exista user in grup");
            GroupMembersEntity groupMembersEntity;
            try {
                groupMembersEntity = groupMembersService.addMember(groupId, userId);
            } catch (SQLException f) {
                f.printStackTrace();
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            if (groupMembersEntity != null) {
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
        // _ api v1 groups
        if (words.length == 4) {
            List<GroupEntity> groups;
            try {
                groups = groupService.getAllGroups();
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            String responseBody;
            try {
                responseBody = objectMapper.writeValueAsString(groups);
            } catch (Exception e) {
                out.println("Bad request");
                out.close();
                resp.setStatus(400);
                return;
            }
            resp.setStatus(200);
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("test/plain");
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        String email = req.getAttribute("email").toString();
        var words = req.getRequestURI().split("/");
        String groupName = words[4];
        UserEntity entity = null;
        try {
            entity = userService.findByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GroupMembersEntity groupMembersEntity;
        GroupEntity groupEntity;
        try {
            groupEntity = groupService.findByName(groupName);
            groupMembersEntity = groupMembersService.findMemberInAGroup(entity.getId(), groupEntity.getId());
            groupMembersService.deleteMember(groupMembersEntity.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println("You are not in this group");
            out.close();
            resp.setStatus(400);
            return;
        }
        if(groupMembersEntity == null || groupEntity==null){
            out.println("Bad Request");
            out.close();
            resp.setStatus(400);
            return;
        }
        out.println("You left the group");
        out.close();
        resp.setStatus(200);
    }

}
