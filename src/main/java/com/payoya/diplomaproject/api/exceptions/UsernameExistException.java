package com.payoya.diplomaproject.api.exceptions;

public class UsernameExistException extends RuntimeException {
    public UsernameExistException(String message){
        super(message);

    }
}
