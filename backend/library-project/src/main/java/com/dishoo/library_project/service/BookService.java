package com.dishoo.library_project.service;

import com.dishoo.library_project.dao.BookRepository;
import com.dishoo.library_project.dao.CheckoutRepository;
import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.entity.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;


    public Book checkoutBook (String userEmail, Long bookId) throws Exception {
        var book = bookRepository.findById(bookId);
        var checkoutValidation = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || checkoutValidation != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book not found or already checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        var checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()

        );

        checkoutRepository.save(checkout);

        return book.get();
    }

    public boolean checkoutBookByUser(String userEmail, Long bookId) {
        var checkoutValidation = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return checkoutValidation != null;
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

}
