package com.maids.test.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMsg", message);
        map.put("responseCode", status.value());
        map.put("transactionDetails", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> multipleResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMsg", message);
        map.put("responseCode", status.value());
        map.put("responseDetails", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> singleResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMsg", message);
        map.put("responseCode", status.value());

        return new ResponseEntity<Object>(map,status);
    }
}
