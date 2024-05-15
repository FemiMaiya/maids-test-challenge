package com.maids.test.librarymanagementsystem.dto.patronManagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePatronRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String modifiedBy;
}
