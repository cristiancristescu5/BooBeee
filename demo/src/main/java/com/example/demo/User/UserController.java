package com.example.demo.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/users", "/users/*"})
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/JSON");
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        if (request.getRequestURI().split("/").length == 5) {
            String id = request.getRequestURI().split("/")[4];
            System.out.println(id);
            Long userId = Long.parseLong(id);
            boolean ok = true;
            System.out.println(userId);
            String userString = new String();
            try {
                UserEntity user = userService.getUserById(userId);
                userString = objectMapper.writeValueAsString(user);
            } catch (IllegalArgumentException e) {
                response.setStatus(204);
                out.println(e.getMessage());
                out.close();
                ok = false;
            }
            if (ok) {
                out.println(userString);
                out.close();
            }
        } else if (request.getRequestURI().split("/").length == 4) {
            List<UserEntity> userEntityList = userService.getAllUsers();
            String responseBody = objectMapper.writeValueAsString(userEntityList);
            out.println(responseBody);
            out.close();
        }else{
            response.setStatus(400);
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("PUT")) {
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long userId = Long.parseLong(id);
            BufferedReader buff = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = buff.readLine()) != null) {
                body.append(line);
            }
            buff.close();

            String reqBody = body.toString();
            UserEntity user = objectMapper.readValue(reqBody, UserEntity.class);
            UserEntity userEntity = userService.updateUser(userId, user);
            String responseBody = objectMapper.writeValueAsString(userEntity);
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getRequestURI().split("/").length==4 && request.getMethod().equals("POST")) {
            response.setContentType("application/JSON");
            response.setStatus(201);
            BufferedReader buff = request.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = buff.readLine()) != null) {
                body.append(line);
            }
            buff.close();

            String reqBody = body.toString();
            UserEntity user = objectMapper.readValue(reqBody, UserEntity.class);
            UserEntity userEntity = userService.addUser(user);
            PrintWriter out = response.getWriter();
            String responseBody = objectMapper.writeValueAsString(userEntity);
            out.println(responseBody);
            out.close();
        }else {
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURI().split("/").length==5 && req.getMethod().equals("DELETE")) {
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long userId = Long.parseLong(id);
            userService.deleteUserById(userId);
            out.println("User deleted");
            out.close();
        }else{
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }
}