package com.example.demo.Config;

import com.example.demo.PasswordUtils;
import com.example.demo.RequestBodyParser;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private static final UserService userService = new UserService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(201);
        PrintWriter out = resp.getWriter();
        String requestBody = RequestBodyParser.parseRequest(req);
        UserEntity user = objectMapper.readValue(requestBody, UserEntity.class);
        if(user.getPassword().length()<16){
            resp.setStatus(400);
            out.println("Password must be at least 16 characters long");
            out.close();
            return;
        }

        if(!isValidEmail(user.getEmail())){
            resp.setStatus(400);
            out.println("Invalid email");
            out.close();
            return;
        }
        user.setCreatedat(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedat(new Timestamp(System.currentTimeMillis()));
        try {
            user.setPassword(PasswordUtils.encryptString(user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            if(userService.existsUserByEmail(user.getEmail())){
                resp.setStatus(409);
                out.println("User with this email already exists");
                out.close();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(userService.existsUserByName(user.getName())){
                resp.setStatus(409);
                out.println("User with this username already exists");
                out.close();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userService.addUser(user);
            out.println(objectMapper.writeValueAsString(user));
            out.close();
        }catch (IllegalArgumentException | SQLException e){
            resp.setStatus(400);
            out.println(e.getMessage());
            out.close();
        }
    }
}
