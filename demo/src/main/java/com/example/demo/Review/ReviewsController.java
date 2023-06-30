package com.example.demo.Review;

import com.example.demo.Comment.CommentEntity;
import com.example.demo.Comment.CommentService;
import com.example.demo.RequestBodyParser;
import com.example.demo.ReviewComment.ReviewCommentEntity;
import com.example.demo.ReviewComment.ReviewCommentService;
import com.example.demo.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/reviews", "/reviews/*"})
public class ReviewsController extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ReviewCommentService reviewCommentService = new ReviewCommentService();
    private final UserService userService = new UserService();
    private final CommentService commentService = new CommentService();

    // /api/v1/reviews/{bookId}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var words = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        if (words[3].equals("reviews") && words.length == 5) {
            String id = words[4];
            Long bookId = Long.parseLong(id);
            String responseBody;
            try {
                List<ReviewEntity> reviewEntities = reviewService.findReviewsByBookId(bookId);
                responseBody = objectMapper.writeValueAsString(reviewEntities);
            } catch (Exception e) {
                resp.setStatus(204);
                out.println(e.getMessage());
                out.close();
                return;
            }
            resp.setStatus(200);
            out.println(responseBody);
            out.close();
            return;
        }
        // /api/v1/reviews/{bookId}/{reviewId}/comments
        if (words[6].equals("comments") && words.length==7) {
            String id1 = words[4];
            Long bookId = Long.parseLong(id1);
            String id2 = words[5];
            Long reviewId = Long.parseLong(id2);
            String responseBody;
            try {
                List<CommentEntity> commentEntities = reviewCommentService.findCommentsByReviewId(reviewId);
                responseBody = objectMapper.writeValueAsString(commentEntities);
            } catch (Exception e) {
                out.println(e.getMessage());
                resp.setStatus(204);
                out.close();
                return;
            }
            resp.setStatus(200);
            out.println(responseBody);
            out.close();
            return;
        }
        resp.setStatus(400);
        out.println("Bad request");
        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(201);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        var words = req.getRequestURI().split("/");
        // /api/v1/reviews/{bookId}
        if(words.length==5 && words[3].equals("reviews")){
            String responseBody;
            try {
                String bookId = words[4];
                Long bId = Long.parseLong(bookId);
                Object email = req.getAttribute("email");
                String userEmail = email.toString();
                Long userId = userService.findByEmail(userEmail).getId();
                ReviewEntity reviewEntity = objectMapper.readValue(RequestBodyParser.parseRequest(req), ReviewEntity.class);
                reviewEntity.setBookId(bId);
                reviewEntity.setUserId(userId);
                ReviewEntity review = reviewService.create(reviewEntity);
                responseBody = objectMapper.writeValueAsString(review);
            } catch (Exception e) {
                out.println(e.getMessage());
                out.close();
                resp.setStatus(400);
                return;
            }
            out.println(responseBody);
            out.close();
            return;
        }
        // /api/v1/reviews/{booksId}/{reviewId}
        if(words.length==6){
            String responseBody;
            try {
                Long reviewId = Long.parseLong(words[6]);
//                Long bookId = Long.parseLong(words[4]);
                Object email = req.getAttribute("email");
                Long userId = userService.findByEmail(email.toString()).getId();
                CommentEntity comment = objectMapper.readValue(RequestBodyParser.parseRequest(req), CommentEntity.class);
                CommentEntity commentEntity = commentService.addComment(comment);
                responseBody = objectMapper.writeValueAsString(commentEntity);
                ReviewCommentEntity entity = reviewCommentService.addCommentToReview(userId, reviewId, commentEntity.getId());
            } catch (Exception e) {
                out.println(e.getMessage());
                out.close();
                resp.setStatus(400);
                return;
            }
            out.println(responseBody);
            out.close();
            resp.setStatus(201);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
