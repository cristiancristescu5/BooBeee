package com.example.demo;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setStatus(200);
        // Hello
        PrintWriter out = response.getWriter();
        if(!request.getRequestURI().equals("/hello")){
            out.println("Hello World");
        }else{
            response.setStatus(204);
            out.println("Not hello world");
        }
        out.close();
    }

    public void destroy() {
    }
}