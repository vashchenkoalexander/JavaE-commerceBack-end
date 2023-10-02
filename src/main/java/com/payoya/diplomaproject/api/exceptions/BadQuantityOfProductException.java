package com.payoya.diplomaproject.api.exceptions;

public class BadQuantityOfProductException extends RuntimeException {

    public BadQuantityOfProductException(String message){
        super(message);
    }

}
