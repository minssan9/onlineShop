package com.minssan9.shop.exception;

public class UnAutorizedException extends RuntimeException {

    public UnAutorizedException() {
        super();
    }

    public UnAutorizedException(String message) {
        super(message);
    }
}
