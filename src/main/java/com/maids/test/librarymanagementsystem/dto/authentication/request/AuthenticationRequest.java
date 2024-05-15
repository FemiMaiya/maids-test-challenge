package com.maids.test.librarymanagementsystem.dto.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationRequest {

    private String userName;
    private String password;

}
