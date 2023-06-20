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
import java.io.Serializable;

@WebServlet (urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static final UserService userService = new UserService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static class LoginMessage implements Serializable {
        private String email;
        private String password;
        public LoginMessage(String email, String password){
            this.email = email;
            this.password = password;
        }
        public LoginMessage(){}
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    private LoginMessage getMessage(HttpServletRequest req) throws IOException {
        String requestBody = RequestBodyParser.parseRequest(req);
        return objectMapper.readValue(requestBody, LoginMessage.class);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/JSON");
        resp.setStatus(200);
        LoginMessage login = getMessage(req);
        String email = login.getEmail();
        String password = login.getPassword();
        PrintWriter out = resp.getWriter();
        if(!userService.existsUserByEmail(email)){
            resp.setStatus(404);
            out.println("User with this email does not exist");
            out.close();
            return;
        }
        UserEntity user = userService.findByEmail(email);
        String userPassword;
        try {
            userPassword = PasswordUtils.decryptString(user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!userPassword.equals(password)){
            resp.setStatus(401);
            out.println("Wrong password");
            out.close();
            return;
        }
        out.println("Login successful");
        out.close();
        //TODO: add token
    }
}
