package com.dishoo.library_project.controller;

import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.responsemodels.ShelfCurrentLoansResponse;
import com.dishoo.library_project.service.BookService;
import com.dishoo.library_project.utils.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("https://library-service-react-961766531040.europe-west1.run.app")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
            throws Exception
    {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        return bookService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long bookId) throws Exception {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook (@RequestHeader(value = "Authorization") String token,
                              @RequestParam Long bookId) throws Exception {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token,
                          @RequestParam Long bookId) throws Exception {
        String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");
        bookService.renewLoan(userEmail, bookId);
    }

}
