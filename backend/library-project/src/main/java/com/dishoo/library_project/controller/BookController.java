package com.dishoo.library_project.controller;

import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PutMapping("/secure/checkout")
    public Book checkoutBook (@RequestParam Long bookId) throws Exception {
        var userEmail = "testuser@mail.com";
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount() {
        var userEmail = "testuser@mail.com";
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public boolean checkoutBookByUser(@RequestParam Long bookId) {
        var userEmail = "testuser@mail.com";
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

}
