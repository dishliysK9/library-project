package com.dishoo.library_project.service;

import com.dishoo.library_project.dao.BookRepository;
import com.dishoo.library_project.dao.CheckoutRepository;
import com.dishoo.library_project.dao.HistoryRepository;
import com.dishoo.library_project.dao.UserRepository;
import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.entity.Checkout;
import com.dishoo.library_project.entity.History;
import com.dishoo.library_project.entity.User;
import com.dishoo.library_project.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    public Book checkoutBook (String userEmail, Long bookId) throws Exception {
        var book = bookRepository.findById(bookId);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        var checkoutValidation = checkoutRepository.findByUserIdAndBookId(user.getId(), bookId);

        if (!book.isPresent() || checkoutValidation != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book not found or already checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        var checkout = new Checkout();
        checkout.setUser(user);
        checkout.setBook(book.get());
        checkout.setCheckoutDate(LocalDate.now().toString());
        checkout.setReturnDate(LocalDate.now().plusDays(7).toString());

        checkoutRepository.save(checkout);

        return book.get();
    }

    public boolean checkoutBookByUser(String userEmail, Long bookId) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        var checkoutValidation = checkoutRepository.findByUserIdAndBookId(user.getId(), bookId);
        return checkoutValidation != null;
    }

    public int currentLoansCount(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        return checkoutRepository.findBooksByUserId(user.getId()).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        var checkoutList = checkoutRepository.findBooksByUserId(user.getId());
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout i: checkoutList) {
            bookIdList.add(i.getBook().getId());
        }

        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getBook().getId() == book.getId()).findFirst();

            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time));
            }
        }
        return shelfCurrentLoansResponses;
    }

    public void returnBook (String userEmail, Long bookId) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        Optional<Book> book = bookRepository.findById(bookId);

        var validateCheckout = checkoutRepository.findByUserIdAndBookId(user.getId(), bookId);

        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                user,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );

        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long bookId) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        var validateCheckout = checkoutRepository.findByUserIdAndBookId(user.getId(), bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }

}
