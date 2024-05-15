package com.maids.test.librarymanagementsystem.dto.bookManagement.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateBookResponse {

    private String message;
    private String dateModified;
    private String timeModified;
    private String modifiedBy;
}
