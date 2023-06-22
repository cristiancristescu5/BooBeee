package com.example.demo.Config;

import com.example.demo.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.NoResultException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
public class AuthenticationFilter implements Filter {
    private static final String SECRET_KEY = "wsdefrgthyjutrefwderetrhgnjmk12w3e4r5t6y7u8i9o0p";
    private static final UserService userService = new UserService();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("aiiiiiiiiiiiiiiiiiiiici");
        HttpServletRequest request = ((HttpServletRequest)servletRequest);
        HttpServletResponse response = ((HttpServletResponse)servletResponse);
        var words = request.getRequestURI().split("/");
        if(request.getMethod().equals("POST") || (request.getMethod().equals("GET") && words[3].equals("users"))) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(token);
                    String email = claimsJws.getBody().getSubject();
                    if (isUserPresent(email)) {
                        request.setAttribute("email", email);
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
    private boolean isUserPresent(String email){
        try{
            var user = userService.findByEmail(email);
        }catch (NoResultException e){
            return false;
        }
        return true;
    }

}
