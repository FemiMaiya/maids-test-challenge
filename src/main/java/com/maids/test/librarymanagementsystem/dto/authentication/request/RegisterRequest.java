package com.maids.test.librarymanagementsystem.dto.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
