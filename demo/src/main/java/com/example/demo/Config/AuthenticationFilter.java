package com.example.demo.Config;

import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.NoResultException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter
public class AuthenticationFilter implements Filter {
    private static final String SECRET_KEY = "wsdefrgthyjutrefwderetrhgnjmk12w3e4r5t6y7u8i9o0p";
    private static final UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        var words = request.getRequestURI().split("/");
        if (request.getMethod().equals("POST") || (request.getMethod().equals("GET") && words[3].equals("users"))
                || (request.getMethod().equals("GET") && words[3].equals("export-as-CSV")
                || (request.getMethod().equals("GET") && words[3].equals("export-as-docBook")))
                || (request.getMethod().equals("PUT"))
                || (request.getMethod().equals("DELETE"))) {
            Cookie[] cookies = request.getCookies();
            String authHeader = new String();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("sessionId")) {
                        authHeader = cookie.getValue();
                    }
                }
            }
            if (authHeader != null && cookies != null) {
                try {
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(authHeader);
                    String email = claimsJws.getBody().getSubject();
                    if (isUserPresent(email)) {
                        request.setAttribute("email", email);
                        System.out.println(email);
                        filterChain.doFilter(request, response);
                    } else {
                        System.out.println("aici2");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } catch (Exception e) {
                    System.out.println("aici exception");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                System.out.println("aici1");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isUserPresent(String email) {
        UserEntity user = null;
        try{
            user = userService.findByEmail(email);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return user!=null;
    }

}
