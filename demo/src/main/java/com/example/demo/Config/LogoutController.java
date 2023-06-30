package com.example.demo.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        out.println("Logout successful");
        out.close();
//        Cookie[] cookies = req.getCookies();
//        Cookie cookie = null;
//        resp.setContentType("text/plain");
//        if(cookies!=null){
//            for(int i = 0 ; i < cookies.length ; i++){
//                if(cookies[i].getName().equals("sessionId")){
//                    cookie = cookies[i];
//                }
//            }
//        }else{
//            resp.setStatus(400);
//            out.println("Bad Request");
//            out.close();
//            return;
//        }
//        if(cookie !=null) {
//            cookie.setMaxAge((int) System.currentTimeMillis() - 999999999);
//        }else{
//            resp.setStatus(400);
//            out.println("Bad Request");
//            out.close();
//            return;
//        }
    }
}
