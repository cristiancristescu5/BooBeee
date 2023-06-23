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
        UserEntity user = userService.findByEmail(email);
        resp.setContentType("application/json");
        Long bookId = Long.parseLong(words[4].trim());
        String bookStatus = words[5];
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals(bookStatus)) {
                break;
            }
            if (i == statuses.length - 1) {
                resp.setStatus(400);
                out.println("Bad Request");
                out.close();
                return;
            }
        }
        try {
            System.out.println("verifiic");
            BookStatusEntity bookStatusEntity = bookStatusService.getBookStatusByBookIdAndStatusAndUserEmail(bookId, email);
            System.out.println("verifiic dupa ce execur");
            resp.setStatus(409);
            out.println("Book already exists");
            out.close();
        } catch (NoResultException e) {
            BookStatusEntity bookStatusEntity = bookStatusService.addBookStatus(new BookStatusEntity(user.getId(), bookId, bookStatus));
            responseBody = objectMapper.writeValueAsString(bookStatusEntity);
            out.println(responseBody);
            out.close();
            return;
        }
    }

}
