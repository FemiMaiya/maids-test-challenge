package com.maids.test.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private String responseCode = "00";
    private String responseMsg = "SUCCESSFUL";
    private T responseDetails;
    //    private Map<String, Object> errors;
    public ResponseWrapper() {  }
    /**   * For the default response message passing only th body   *   * @param body   */
    public ResponseWrapper(T body) {    setSuccessParams(body);  }
    /**   * For response with the body and custom messages   *   * @param body   * @param message   */
    public ResponseWrapper(T body, String message)
    {
        setSuccessParams(body, message);
    }

    public ResponseWrapper(T body, String message, String code) {
        this.responseCode = code;
        setSuccessParams(body, message);
    }
    private void setSuccessParams(T body, String message) {
        setResponseMsg(message);
        setResponseDetails(body);
    }
    private void setSuccessParams(T body) {
        setSuccessParams(body, responseMsg);
    }}