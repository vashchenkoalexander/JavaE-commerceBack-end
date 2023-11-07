package com.payoya.diplomaproject.api.exceptions;

public class ZeroItemOrderException extends RuntimeException {

    public ZeroItemOrderException(String message){
        super(message);
    }

}
