package com.example.demo.Exports;

import com.example.demo.Book.BookEntity;
import com.example.demo.Book.BookService;
import com.example.demo.BookAuthors.BookAuthorsEntity;
import com.example.demo.BookAuthors.BookAuthorsService;
import com.example.demo.BookGenres.BookGenresEntity;
import com.example.demo.BookGenres.BookGenresService;
import com.example.demo.BookStatus.BookStatusRepository;
import com.example.demo.BookStatus.BookStatusService;
import com.example.demo.Group.GroupEntity;
import com.example.demo.Group.GroupService;
import com.example.demo.Review.ReviewService;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.*;

public class ExportMessageService {
    private final ReviewService reviewService = new ReviewService();
    private final BookService bookService = new BookService();
    private final BookAuthorsService bookAuthorsService = new BookAuthorsService();
    private final BookStatusRepository bookStatusRepository = new BookStatusRepository();
    private final BookGenresService bookGenresService = new BookGenresService();
    private final GroupService groupService = new GroupService();

    public List<ExportMessage> getExportMessages() throws SQLException {
        List<ExportMessage> exportMessages = new ArrayList<>();
        Map<BookEntity, Float> books = new HashMap<>();
        BookEntity bookMax = new BookEntity();
        BookEntity bookMin = new BookEntity();
        BookEntity bookRMax = new BookEntity();
        BookEntity bookRMin = new BookEntity();
        BookEntity bookR = new BookEntity();
        BookEntity bookW = new BookEntity();
        BookEntity bookF = new BookEntity();
        Float maxRating = 0.0f;
        Float minRating = Float.MAX_VALUE;
        Long maxReviews = Long.MIN_VALUE;
        Long minReviews = Long.MAX_VALUE;
        Integer maxF = Integer.MIN_VALUE;
        Integer maxR = Integer.MIN_VALUE;
        Integer maxW = Integer.MIN_VALUE;
        for(BookEntity b : bookService.getAllBooks()){
            Float rating = reviewService.getBookRating(b.getId());
            Long reviews =(long) reviewService.findReviewsByBookId(b.getId()).size();
            Integer statusF= bookStatusRepository.findByBookIdAndStatus(b.getId(), "Finished").size();
            Integer statusR = bookStatusRepository.findByBookIdAndStatus(b.getId(), "Reading").size();
            Integer statusW = bookStatusRepository.findByBookIdAndStatus(b.getId(), "Wish").size();
            if(rating>maxRating){
                bookMax = b;
                maxRating = rating;
            }
            if(rating < minRating){
                bookMin = b;
                minRating = rating;
            }
            if(reviews > maxReviews){
                maxReviews = reviews;
                bookRMax = b;
            }
            if(reviews < minReviews){
                minReviews = reviews;
                bookRMin = b;
            }
            if(maxF < statusF){
                maxF = statusF;
                bookF = b;
            }
            if(maxR < statusR){
                maxR = statusR;
                bookR = b;
            }
            if(maxW < statusW){
                maxW = statusW;
                bookW = b;
            }
            books.put(b, rating);
        }
        List<GroupEntity> groupEntities = groupService.getAllGroups();
        int max = Integer.MIN_VALUE;
        GroupEntity group = new GroupEntity();
        for(GroupEntity g : groupEntities){
            if(g.getMembersCount()>max){
                max = g.getMembersCount();
                group = g;
            }
        }
        exportMessages.add(new ExportMessage("Highest Rated Book: "+ bookMax.getTitle() + "."));
        exportMessages.add(new ExportMessage("Lowest Rated Book: "+ bookMin.getTitle()+"."));
        exportMessages.add(new ExportMessage("The Most Reviewed Book: " + bookRMax.getTitle()+"."));
        exportMessages.add(new ExportMessage("The Least Reviewed Book: " + bookRMin.getTitle()+"."));
        exportMessages.add(new ExportMessage("The Most Popular Book: "+bookR.getTitle()+"." ));
        exportMessages.add(new ExportMessage("The Most Wished Book: "+ bookW.getTitle()+"."));
        exportMessages.add(new ExportMessage("The Most Read Book: "+bookF.getTitle()+ "."));
        exportMessages.add(new ExportMessage("The Most Popular Author: "+ bookAuthorsService.getAuthorByBookId(bookR.getId()).getName()+"."));
        exportMessages.add(new ExportMessage("The Most Popular Book Genre: "+ bookGenresService.findGenreByBookId(bookR.getId()).getName()+"."));
        exportMessages.add(new ExportMessage("The Biggest Group: "+ group.getName()+", with "+max+" members."));
//        Collections.sort(books, (b1, b2)->{
//            return books.get(b1)>books.get(b2);
//        });
        return exportMessages;
    }
}
