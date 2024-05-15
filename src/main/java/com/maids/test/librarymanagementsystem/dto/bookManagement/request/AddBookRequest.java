package com.maids.test.librarymanagementsystem.dto.bookManagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddBookRequest {

    private String bookTitle;
    private String author;
    private String yearPublished;
    private String isbn;
    private String createdBy;
}
