package com.maids.test.librarymanagementsystem.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {

    private String responseMsg;
    private String responseCode;
}
