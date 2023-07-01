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
import java.util.Arrays;

@WebFilter
public class AdminFilter implements Filter {
    private static final String SECRET_KEY = "wsdefrgthyjutrefwderetrhgnjmk12w3e4r5t6y7u8i9o0p";
    private static final UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        var words = request.getRequestURI().split("/");
        System.out.println(Arrays.toString(words));
        Cookie[] cookies = request.getCookies();
        String auth = null;
        if (request.getMethod().equals("POST")) {
            System.out.println("filtrez adminiiiii");
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("sessionId")) {
                        auth = cookie.getValue();
                    }
                }
            }
            System.out.println(auth);

            if (auth != null) {
                try {
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(auth);
                    String email = claimsJws.getBody().getSubject();
                    if (isUserAdmin(email)) {
                        request.setAttribute("email", email);
                        System.out.println(email);
                        filterChain.doFilter(request, response);
                    } else {
                        System.out.println("not admin");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } catch (Exception e) {
                    System.out.println("not admin");
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                System.out.println("not admin");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isUserAdmin(String email) throws SQLException {
        UserEntity user;
        try {
            user = userService.findByEmail(email);
        } catch (SQLException e) {
            return false;
        }
        System.out.println(user.toString());
        return user.isAdmin();
    }


}
