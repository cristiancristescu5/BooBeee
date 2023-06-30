package com.example.demo.Config;

import com.example.demo.User.UserEntity;
import com.example.demo.User.UserService;
import jakarta.persistence.NoResultException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminFilter implements Filter {
    private static final String SECRET_KEY = "wsdefrgthyjutrefwderetrhgnjmk12w3e4r5t6y7u8i9o0p";
    private static final UserService userService = new UserService();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        var words = request.getRequestURI().split("/");

    }
    private boolean isUserAdmin(String email) throws SQLException {
        UserEntity user;
        try {
             user = userService.findByEmail(email);
        }catch (SQLException e){
            return false;
        }
        return user != null && user.isAdmin();
    }


}
