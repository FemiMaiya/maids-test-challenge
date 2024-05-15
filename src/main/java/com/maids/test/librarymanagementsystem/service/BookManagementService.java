package com.maids.test.librarymanagementsystem.service;

import com.maids.test.librarymanagementsystem.dto.bookManagement.request.AddBookRequest;
import com.maids.test.librarymanagementsystem.dto.bookManagement.response.AddBookResponse;
import com.maids.test.librarymanagementsystem.dto.bookManagement.request.UpdateBookRequest;
import com.maids.test.librarymanagementsystem.dto.bookManagement.response.UpdateBookResponse;
import com.maids.test.librarymanagementsystem.entity.BookRecordsEntity;
import com.maids.test.librarymanagementsystem.exceptions.InternalServerException;
import com.maids.test.librarymanagementsystem.exceptions.MyCustomException;
import com.maids.test.librarymanagementsystem.repository.BookRecordRepository;
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
public class BookManagementService {

    //Constant utility class declaration for validations and regex
    private final Constant constant;

    //Helper utility class declaration
    private final HelperUtil helperUtil;

    //Book management repo declaration
    private final BookRecordRepository bookRecordRepository;

    public Object getBookById (String id) {

        log.info("\n GETTING BOOK BY ID......");

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID BOOK ID PASSED. KINDLY INPUT A VALID BOOK ID";
            throw new MyCustomException(message);
        }

        //Returning book entity
        Optional<BookRecordsEntity> book;
        try {
            book = bookRecordRepository.findById(Long.parseLong(id));
            log.info("BOOK RETURNED: " + book);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (book.isEmpty()) {
            log.info("Book does not exist");
            String message = "BOOK DOES NOT EXIST";
            throw new MyCustomException(message);
        }

        return book;
    }

    public Object addBook (AddBookRequest request) {
        log.info("\nADDING BOOK......");
        log.info("REQUEST PASSED {} ", request);

        //Validating if ISBN exists
        BookRecordsEntity isbn;
        try {
            isbn = bookRecordRepository.checkIsbn(request.getIsbn());
            log.info("ISBN RETURNED: " + isbn);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (isbn != null) {
            log.info("ISBN exists");
            throw new MyCustomException("ISBN PROVIDED ALREADY EXISTS.");
        }

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        //Building book entity
        BookRecordsEntity bookEntity = BookRecordsEntity.builder()
                .bookTitle(request.getBookTitle().toUpperCase())
                .author(request.getAuthor().toUpperCase())
                .yearPublished(request.getYearPublished())
                .isbn(request.getIsbn())
                .dateCreated(date)
                .timeCreated(time)
                .createdBy(request.getCreatedBy())
                .dateModified(date)
                .timeModified(time)
                .modifiedBy(request.getCreatedBy())
                .build();

        try {
            bookRecordRepository.save(bookEntity);
        }catch (Exception exception) {
            log.info("UNABLE TO SAVE RECORDS TO DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        //Building response
        return AddBookResponse.builder()
                .bookId(String.valueOf(bookEntity.getId()))
                .bookTitle(bookEntity.getBookTitle())
                .author(bookEntity.getAuthor())
                .yearPublished(bookEntity.getYearPublished())
                .isbn(bookEntity.getIsbn())
                .dateCreated(bookEntity.getDateCreated())
                .dateCreated(bookEntity.getDateCreated())
                .timeCreated(bookEntity.getTimeCreated())
                .createdBy(bookEntity.getCreatedBy())
                .build();
    }

    public Object updateBook (String id, UpdateBookRequest request) {
        log.info("\nUPDATING BOOK......");
        log.info("REQUEST PASSED {} ", request);

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID BOOK ID PASSED. KINDLY INPUT A VALID BOOK ID";
            throw new MyCustomException(message);
        }


        //Returning book entity
        Optional<BookRecordsEntity> book;
        try {
            book = bookRecordRepository.findById(Long.parseLong(id));
            log.info("BOOK RETURNED: " + book);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        BookRecordsEntity bookRecordsEntity = modifyBookRecords(request, book);

        bookRecordRepository.save(bookRecordsEntity);

        //Building response
        return UpdateBookResponse.builder()
                .message("BOOK UPDATED SUCCESSFULLY")
                .dateModified(bookRecordsEntity.getDateModified())
                .timeModified(bookRecordsEntity.getTimeModified())
                .modifiedBy(bookRecordsEntity.getModifiedBy())
                .build();
    }

    private BookRecordsEntity modifyBookRecords(UpdateBookRequest request, Optional<BookRecordsEntity> book) {

        log.info("MODIFYING BOOK ENTITY");

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        if (book.isEmpty()) {
            log.info("Book does not exist");
            throw new MyCustomException("BOOK WITH ID PASSED DOES NOT EXIST");
        }
        //Modifying book entity returned
        BookRecordsEntity bookRecordsEntity = book.get();

        bookRecordsEntity.setBookTitle(request.getBookTitle().toUpperCase());
        bookRecordsEntity.setAuthor(request.getAuthor().toUpperCase());
        bookRecordsEntity.setYearPublished(request.getYearPublished());
        bookRecordsEntity.setIsbn(request.getIsbn());
        bookRecordsEntity.setModifiedBy(request.getModifiedBy());
        bookRecordsEntity.setDateModified(date);
        bookRecordsEntity.setTimeModified(time);
        return bookRecordsEntity;
    }

    public Object deleteBook (String id) {
        log.info("\nDELETING BOOK......");

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID BOOK ID PASSED. KINDLY INPUT A VALID BOOK ID";
            throw new MyCustomException(message);
        }

        try {
            bookRecordRepository.deleteById(Long.parseLong(id));
        }catch (Exception exception) {
            log.info("UNABLE TO DELETE RECORD FROM DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return "BOOK DELETED SUCCESSFULLY";
    }

    private boolean validateId(String id){

        return constant.validateId(id);
    }
}
