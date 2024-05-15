package com.maids.test.librarymanagementsystem.controller;

import com.maids.test.librarymanagementsystem.dto.BaseResponse;
import com.maids.test.librarymanagementsystem.dto.bookManagement.request.AddBookRequest;
import com.maids.test.librarymanagementsystem.dto.bookManagement.request.UpdateBookRequest;
import com.maids.test.librarymanagementsystem.dto.patronManagement.request.AddPatronRequest;
import com.maids.test.librarymanagementsystem.dto.patronManagement.request.UpdatePatronRequest;
import com.maids.test.librarymanagementsystem.entity.BookRecordsEntity;
import com.maids.test.librarymanagementsystem.entity.PatronManagementEntity;
import com.maids.test.librarymanagementsystem.exceptions.InternalServerException;
import com.maids.test.librarymanagementsystem.repository.BookRecordRepository;
import com.maids.test.librarymanagementsystem.repository.PatronManagementReposiory;
import com.maids.test.librarymanagementsystem.service.BookManagementService;
import com.maids.test.librarymanagementsystem.service.BorrowManagementService;
import com.maids.test.librarymanagementsystem.service.PatronManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("maids-integration-test/library-management")
public class LibraryManagementController {

    //Service class declaration
    private final BookManagementService bookManagementService;
    private final PatronManagementService patronManagementService;
    private final BorrowManagementService borrowManagementService;

    //Repository interface declaration
    private final BookRecordRepository bookRecordRepository;
    private final PatronManagementReposiory patronManagementReposiory;

    //Book management endpoints
    @GetMapping("api/get/books")
    public BaseResponse<Object> getAllBooks() {

        log.info("\nGETTING ALL BOOKS......");

        List<BookRecordsEntity> allBooks;
        try {
            allBooks = bookRecordRepository.findAll();
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return new BaseResponse<>(allBooks);
    }

    @GetMapping("api/get/book/{id}")
    public BaseResponse<Object> getBook(@PathVariable("id") String id) {

        return new BaseResponse<>(bookManagementService.getBookById(id));
    }

    @PostMapping("api/add/book")
    public BaseResponse<Object> addNewBook(@RequestBody AddBookRequest request) {

        return new BaseResponse<>(bookManagementService.addBook(request));
    }

    @PutMapping("api/update/book/{id}")
    public BaseResponse<Object> updateBook(@RequestBody UpdateBookRequest request,
                                        @PathVariable("id") String id) {

        return new BaseResponse<>(bookManagementService.updateBook(id, request));
    }

    @DeleteMapping("api/delete/book/{id}")
    public BaseResponse<Object> deleteBook(@PathVariable("id") String id) {

        return new BaseResponse<>(bookManagementService.deleteBook(id));
    }


    //Patron management endpoints
    @GetMapping("api/get/patrons")
    public BaseResponse<Object> getAllPatrons() {

        log.info("\nGETTING ALL PATRONS......");

        List<PatronManagementEntity> allPatrons;
        try {
            allPatrons = patronManagementReposiory.findAll();
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return new BaseResponse<>(allPatrons);
    }

    @GetMapping("api/get/patron/{id}")
    public BaseResponse<Object> getPatron(@PathVariable("id") String id) {

        return new BaseResponse<>(patronManagementService.getPatronById(id));
    }

    @PostMapping("api/add/patron")
    public BaseResponse<Object> addNewPatron(@RequestBody AddPatronRequest request) {

        return new BaseResponse<>(patronManagementService.addPatron(request));
    }

    @PutMapping("api/update/patron/{id}")
    public BaseResponse<Object> updatePatron(@RequestBody UpdatePatronRequest request,
                                           @PathVariable("id") String id) {

        return new BaseResponse<>(patronManagementService.updatePatronDetails(id, request));
    }

    @DeleteMapping("api/delete/patron/{id}")
    public BaseResponse<Object> deletePatron(@PathVariable("id") String id) {

        return new BaseResponse<>(patronManagementService.deletePatron(id));
    }


    //Borrowing endpoints
    @PostMapping("api/borrow/{bookId}/patron/{patronId}")
    public BaseResponse<Object> borrowBook(@PathVariable("bookId") String bookId,
                                           @PathVariable("patronId") String patronId) {

        return new BaseResponse<>(borrowManagementService.borrowBook(bookId, patronId));
    }

    @PutMapping("api/return/book/{borrowId}")
    public BaseResponse<Object> returnBook(@PathVariable("borrowId") String borrowId) {

        return new BaseResponse<>(borrowManagementService.returnBook(borrowId));
    }
}
