package com.maids.test.librarymanagementsystem.dto.bookManagement.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddBookResponse {

    private String bookId;
    private String bookTitle;
    private String author;
    private String yearPublished;
    private String isbn;
    private String dateCreated;
    private String timeCreated;
    private String createdBy;
}
