package com.maids.test.librarymanagementsystem.dto.patronManagement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddPatronResponse {

    private String patronId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateCreated;
    private String timeCreated;
    private String createdBy;
}
