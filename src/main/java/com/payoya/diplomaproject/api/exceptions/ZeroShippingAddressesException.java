package com.payoya.diplomaproject.api.exceptions;

public class ZeroShippingAddressesException extends RuntimeException {

    public ZeroShippingAddressesException(String message){
        super(message);
    }
}
