package com.maids.test.librarymanagementsystem.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// This is the base response class that defines the format of responses in this application

public class BaseResponse<T> extends ResponseEntity<ResponseWrapper<T>> {

    public BaseResponse(HttpStatus status) { super(status); }
    /**   * This is the default successful response structure   *   * @param body   */
    public BaseResponse(T body) {
        super(new ResponseWrapper<>(body), HttpStatus.OK);
    }  /**   * This is for responses with a custom message   *   * @param body   * @param status   */
    public BaseResponse(T body, HttpStatus status) {
        super(new ResponseWrapper<>(body), status);
    }
    public BaseResponse(T body, String code, HttpStatus status) {
        super(new ResponseWrapper<>(body, code), status);
    }  /**   * This is for responses where you want to specify a custom message and http status   *   * @param body   * @param message   */
    public BaseResponse(T body, String message, String code, HttpStatus status) {
        super(new ResponseWrapper<>(body, message, code), status);
    }}