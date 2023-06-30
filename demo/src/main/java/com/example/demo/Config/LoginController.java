package com.example.demo.Config;

import com.example.demo.PasswordUtils;
import com.example.demo.RequestBodyParser;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(urlPatterns = {"/login"})

public class LoginController extends HttpServlet {
    private static final String SECRET_KEY = "wsdefrgthyjutrefwderetrhgnjmk12w3e4r5t6y7u8i9o0p";
    private static final UserService userService = new UserService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static class LoginMessage implements Serializable {
        private String email;
        private String password;

        public LoginMessage(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public LoginMessage() {
        }

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

        @Override
        public String toString() {
            return this.email + " " + this.password;
        }
    }

    public static class JWTMessage implements Serializable {
        private String token;

        public JWTMessage(String token) {
            this.token = token;
        }

        public JWTMessage() {
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private LoginMessage getMessage(HttpServletRequest req) throws IOException {
        String requestBody = RequestBodyParser.parseRequest(req);
        return objectMapper.readValue(requestBody, LoginMessage.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginMessage login = getMessage(req);
        resp.setContentType("application/json");
        resp.setStatus(201);
        System.out.println(login.toString());
        System.out.println("aici");
        String email = login.getEmail();
        String password = login.getPassword();
        PrintWriter out = resp.getWriter();
        try {
            if (!userService.existsUserByEmail(email)) {
                resp.setStatus(404);
                out.println("User with this email does not exist");
                out.close();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserEntity user = null;
        try {
            user = userService.findByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String userPassword;
        try {
            userPassword = PasswordUtils.decryptString(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(400);
            out.println("Wrong password");
            out.close();
            return;
        }
        if (!userPassword.equals(password)) {
            resp.setStatus(401);
            out.println("Wrong password");
            out.close();
            return;
        }
        String token = generateJWTToken(email);
        JWTMessage jwtMessage = new JWTMessage(token);
        String response;
        try {
            response = objectMapper.writeValueAsString(jwtMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Cookie cookie = new Cookie("sessionId", token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge((int)System.currentTimeMillis()+864_000);
        cookie.setAttribute("Same-Site", "Strict");
        resp.addCookie(cookie);
        out.println(response);
        out.close();
    }

    private String generateJWTToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 864000000);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
