package com.maids.test.librarymanagementsystem.dto.patronManagement.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdatePatronResponse {

    private String message;
    private String dateModified;
    private String timeModified;
    private String modifiedBy;
}
