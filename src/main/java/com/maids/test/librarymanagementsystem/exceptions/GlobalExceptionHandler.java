package com.maids.test.librarymanagementsystem.exceptions;

import com.google.gson.Gson;
import com.maids.test.librarymanagementsystem.dto.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Gson gson;
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("responseCode", status.value());

        body.put("responseMsg", "Invalid request body");

        //To get all errors
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x ->  x.getDefaultMessage()).collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleException(BadRequestException e) {
        e.printStackTrace();
        ResponseEntity<ResponseWrapper<Object>> error = new ResponseEntity<>(errorResponseBuilder("01", e.getMessage(), e.getValidation()), HttpStatus.BAD_REQUEST);
        log.error("Exception - {}", gson.toJson(error.getBody()));
        return error;
    }

    private static ResponseWrapper<Object> errorResponseBuilder(String code, String message, Map<String, Object> validation){
        ResponseWrapper<Object> response = new ResponseWrapper<>();
        response.setResponseCode(code);
        response.setResponseMsg(message);
//        response.setErrors(validation);
        return response;
    }

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<CustomException> handleMyCustomException(final MyCustomException ex) {
        log.info("cause: " + ex.toString());
        CustomException response = new CustomException();
        response.setResponseMsg(ex.getMessage());
        response.setResponseCode("99");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomException> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.info("cause: " + ex.toString());
        CustomException response = new CustomException();
        response.setResponseMsg("illegal exception");
        response.setResponseCode("99");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<CustomizedException> myDuplicateException(final DuplicateException ex) {
        CustomizedException customizedException = new CustomizedException();
        customizedException.setResponseMsg(ex.getMessage());
        customizedException.setResponseCode("77");
        return new ResponseEntity<>(customizedException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomException> handleUnauthorizedException(final UnauthorizedException ex) {
        log.info("cause: " + ex.toString());
        CustomException response = new CustomException();
        response.setResponseMsg(ex.getMessage());
        response.setResponseCode("99");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
