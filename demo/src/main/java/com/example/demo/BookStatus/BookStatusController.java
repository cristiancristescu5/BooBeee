package com.example.demo.BookStatus;

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

@WebServlet(urlPatterns = "/addBookStatus/*")
public class BookStatusController extends HttpServlet {
    private final BookStatusService bookStatusService = new BookStatusService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();
    private final String[] statuses = {"Reading", "Wish", "Finished"};

    // /api/v1/addBookStatus/{bookId}/{status}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getAttribute("email").toString();
        PrintWriter out = resp.getWriter();
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        String responseBody;
        UserEntity user = null;
        try {
            user = userService.findByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setContentType("application/json");
        Long bookId = Long.parseLong(words[4].trim());
        String bookStatus = words[5];
        if(!findStatus(bookStatus)){
            resp.setStatus(400);
            resp.setContentType("text/plain");
            out.println("Bad Request");
            out.close();
            return;
        }
        try {
            System.out.println("verifiic");
            BookStatusEntity bookStatusEntity = bookStatusService.getBookStatusByBookIdAndStatusAndUserEmail(bookId, email);
            System.out.println("verifiic dupa ce execur");
            resp.setStatus(409);
            out.println("Book already exists");
            out.close();
        } catch (Exception e) {
            BookStatusEntity bookStatusEntity = null;
            try {
                bookStatusEntity = bookStatusService.addBookStatus(new BookStatusEntity(user.getId(), bookId, bookStatus));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            responseBody = objectMapper.writeValueAsString(bookStatusEntity);
            out.println(responseBody);
            System.out.println(responseBody);
            String finalResponseBody = resp.getOutputStream().toString();
            System.out.println(finalResponseBody);
            out.close();
        }
    }
    // /api/v1/addBookStatus/{bookId}/{status}
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setStatus(201);
        var words = req.getRequestURI().split("/");
        String responseBody;
        String email = req.getAttribute("email").toString();
        Long userId, bookId;
        String bookStatus = words[5];
        if(!findStatus(bookStatus)){
            resp.setStatus(400);
            resp.setContentType("text/plain");
            out.println("Bad Request");
            out.close();
            return;
        }
        try {
            UserEntity user = userService.findByEmail(email);
            resp.setContentType("application/json");
            bookId = Long.parseLong(words[4].trim());
            userId = user.getId();
        }catch (Exception e){
            System.out.println(e.getMessage());
            resp.setStatus(400);
            resp.setContentType("text/plain");
            out.println("Bad Request");
            out.close();
            return;
        }
        try{
            BookStatusEntity bookStatusEntity = bookStatusService.updateBookStatus(bookStatus, bookId, userId);
            responseBody = objectMapper.writeValueAsString(bookStatusEntity);
        }catch (Exception e){
            System.out.println(e.getMessage());
            resp.setStatus(400);
            resp.setContentType("text/plain");
            out.println("Bad Request");
            out.close();
            return;
        }
        out.println(responseBody);
        out.close();
    }
    private boolean findStatus(String status){
        for (String s : statuses) {
            if (s.equals(status)) {
                return true;
            }
        }
        return false;
    }
}
