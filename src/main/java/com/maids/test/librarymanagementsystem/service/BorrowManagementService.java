package com.maids.test.librarymanagementsystem.service;

import com.maids.test.librarymanagementsystem.dto.borrowBook.response.BorrowBookResponse;
import com.maids.test.librarymanagementsystem.entity.BookRecordsEntity;
import com.maids.test.librarymanagementsystem.entity.BorrowRecordEntity;
import com.maids.test.librarymanagementsystem.entity.PatronManagementEntity;
import com.maids.test.librarymanagementsystem.exceptions.InternalServerException;
import com.maids.test.librarymanagementsystem.exceptions.MyCustomException;
import com.maids.test.librarymanagementsystem.repository.BookRecordRepository;
import com.maids.test.librarymanagementsystem.repository.BorrowRecordRepository;
import com.maids.test.librarymanagementsystem.repository.PatronManagementReposiory;
import com.maids.test.librarymanagementsystem.util.Constant;
import com.maids.test.librarymanagementsystem.util.HelperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowManagementService {

    //Helper utility class declaration
    private final HelperUtil helperUtil;

    private final Constant constant;

    //Patron management repo declaration
    private final PatronManagementReposiory patronManagementReposiory;

    //Book management repo declaration
    private final BookRecordRepository bookRecordRepository;

    private final BorrowRecordRepository borrowRecordRepository;

    public Object borrowBook (String bookId, String patronId) {

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> bookIdVal = CompletableFuture.supplyAsync(() ->
                this.validateId(bookId));
        CompletableFuture<Boolean> patronIdVal = CompletableFuture.supplyAsync(() ->
                this.validateId(patronId));


        boolean bookIdValRes = bookIdVal.join();
        boolean patronIdValRes = patronIdVal.join();

        if (!bookIdValRes) {
            log.info("Invalid BOOK ID passed");
            String message = "INVALID BOOK ID PASSED. KINDLY INPUT A VALID BOOK ID";
            throw new MyCustomException(message);
        }

        if (!patronIdValRes) {
            log.info("Invalid PATRON ID passed");
            String message = "PATRON BOOK ID PASSED. KINDLY INPUT A VALID PATRON ID";
            throw new MyCustomException(message);
        }

        Optional<PatronManagementEntity> patron;
        try {
            patron = patronManagementReposiory.findById(Long.parseLong(patronId));
            log.info("PATRONS RETURNED: " + patron);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (patron.isEmpty()) {
            log.info("Patron does not exist");
            throw new MyCustomException("PATRON WITH ID PASSED DOES NOT EXIST");
        }

        Optional<BookRecordsEntity> book;
        try {
            book = bookRecordRepository.findById(Long.parseLong(bookId));
            log.info("BOOK RETURNED: " + book);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (book.isEmpty()) {
            log.info("Book does not exist");
            throw new MyCustomException("BOOK WITH ID PASSED DOES NOT EXIST");
        }

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        //Generating borrow ID
        String borrowId = "BID-" + date.replace("-", "") + time.replace(":", "")
                + bookId + patronId;

        BorrowRecordEntity borrow = BorrowRecordEntity.builder()
                .BOOK(book.get())
                .PATRON(patron.get())
                .borrowId(borrowId)
                .dateCollected(date)
                .timeCollected(time)
                .status("NOT-RETURNED")
                .build();

        try {
            borrowRecordRepository.save(borrow);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return BorrowBookResponse.builder()
                .borrowId(borrowId)
                .build();
    }

    public Object returnBook (String borrowId) {

        BorrowRecordEntity borrowDetails;
        try {
            borrowDetails = borrowRecordRepository.getBorrowDetails(borrowId);
            log.info("BORROW DETAILS RETURNED: " + borrowDetails);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (borrowDetails == null) {
            log.info("Borrow details not exist");
            throw new MyCustomException("BORROW DETAILS WITH ID PASSED DOES NOT EXIST");
        }

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        borrowDetails.setDateReturned(date);
        borrowDetails.setTimeReturned(time);
        borrowDetails.setStatus("Returned");

        try {
            borrowRecordRepository.save(borrowDetails);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return "BOOK RETURNED SUCCESSFULLY!";
    }

    private boolean validateId(String id){

        return constant.validateId(id);
    }
}
