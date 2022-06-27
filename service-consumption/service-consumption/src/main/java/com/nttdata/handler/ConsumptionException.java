package com.nttdata.handler;

import org.springframework.http.HttpStatus;

public class ConsumptionException extends RuntimeException {

    private HttpStatus httpStatus;

    public ConsumptionException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }



    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
