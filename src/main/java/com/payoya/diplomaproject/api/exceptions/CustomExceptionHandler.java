package com.payoya.diplomaproject.api.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /*
    this method receive bunch of parameters in invalid post mapping and create a response to 400 code message
     of client bad request and return a response entity with headers, status and list of problems with post mapping
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).toList();

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(UsernameExistException ex) {

        return new ResponseEntity<>(getResponseBody(ex), HttpStatus.BAD_REQUEST);
    }


    /*
    handle exceptions from HttpMessageNotReadableException class for now, only in User.Role
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());
        String errors = ex.getMessage();
        responseBody.put("error", errors);
        return new ResponseEntity<>(responseBody, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        String errors = ex.getLocalizedMessage();

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }


    @ExceptionHandler (ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(getResponseBody(e), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ZeroItemOrderException.class)
    protected ResponseEntity<Object> handleZeroItemException(ZeroItemOrderException ex){

        return new ResponseEntity<>(getResponseBody(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ZeroShippingAddressesException.class)
    protected ResponseEntity<Object> handleZeroShippingAddressesException(ZeroShippingAddressesException ex){
        return new ResponseEntity<>(getResponseBody(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductIsNullException.class)
    protected ResponseEntity<Object> handleProductIsNullException(ProductIsNullException exception){
        return new ResponseEntity<>(getResponseBody(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserHisSelfAddingToCartProductException.class)
    protected ResponseEntity<Object> handleUserHisSelfAddingToCartProductException(UserHisSelfAddingToCartProductException exception){
        return new ResponseEntity<>(getResponseBody(exception), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getResponseBody(RuntimeException exception){

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status" , HttpStatus.BAD_REQUEST.value());

        responseBody.put("error", exception.getMessage());

        return responseBody;
    }

}
