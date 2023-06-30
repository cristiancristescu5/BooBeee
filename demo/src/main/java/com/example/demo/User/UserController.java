package com.example.demo.User;

import com.example.demo.BookStatus.BookStatusService;
import com.example.demo.BookStatus.BooksWithStatuses;
import com.example.demo.RequestBodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/users", "/users/*"})
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final BookStatusService bookStatusService = new BookStatusService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/JSON");
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        var words = request.getRequestURI().split("/");
        // /api/v1/users/{userEmail}

        if (words.length == 4) {
            System.out.println(words[3]);
            String email = request.getAttribute("email").toString();
            String userString;
            try {
                UserEntity user = userService.findByEmail(email);
                userString = objectMapper.writeValueAsString(user);
            } catch (IllegalArgumentException e) {
                response.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            out.println(userString);
            out.close();
            return;
        }

        // api/v1/users/books --> aduce lista de carti ale user ului cu tot cu statusurile sale
        if (words.length == 5 && words[4].equals("books") && request.getMethod().equals("GET")) {
            String email = request.getAttribute("email").toString();
            String responseBody;
            try {
                List<BooksWithStatuses> books = bookStatusService.getAllBooks(email);
                responseBody = objectMapper.writeValueAsString(books);
            } catch (Exception e) {
                out.println(e.getMessage());
                out.close();
                response.setStatus(400);
                return;
            }
            out.println(responseBody);
            out.close();
            response.setStatus(200);
            return;
        }
//        if (words.length == 4) {
//            List<UserEntity> userEntityList = userService.getAllUsers();
//            String responseBody = objectMapper.writeValueAsString(userEntityList);
//            out.println(responseBody);
//            out.close();
//            return;
//        }
        response.setStatus(400);
        out.println("Bad request");
        out.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().split("/").length == 5 && req.getMethod().equals("PUT")) {
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long userId = Long.parseLong(id);
            UserEntity user = objectMapper.readValue(RequestBodyParser.parseRequest(req), UserEntity.class);
            UserEntity userEntity;
            try {
                userEntity= userService.updateUser(userId, user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String responseBody = objectMapper.writeValueAsString(userEntity);
            out.println(responseBody);
            out.close();
        } else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().split("/").length == 4 && request.getMethod().equals("POST")) {
            response.setContentType("application/JSON");
            response.setStatus(201);
            BufferedReader buff = request.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            PrintWriter out = response.getWriter();
            while ((line = buff.readLine()) != null) {
                body.append(line);
            }
            buff.close();

            String reqBody = body.toString();
            UserEntity user = objectMapper.readValue(reqBody, UserEntity.class);
            UserEntity userEntity;
            try {
                userEntity = userService.addUser(user);
            }catch (SQLException e){
                e.printStackTrace();
                response.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
            String responseBody = objectMapper.writeValueAsString(userEntity);
            out.println(responseBody);
            out.close();
        } else {
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            out.println("Bad request");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().split("/").length == 5 && req.getMethod().equals("DELETE")) {
            resp.setContentType("application/JSON");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            String id = req.getRequestURI().split("/")[4];
            Long userId = Long.parseLong(id);
            try {
                userService.deleteUserById(userId);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(400);
                out.println("Bad request");
                out.close();
                return;
            }
            out.println("User deleted");
            out.close();
        } else {
            resp.setStatus(400);
            PrintWriter out = resp.getWriter();
            out.println("Bad request");
            out.close();
        }
    }
}