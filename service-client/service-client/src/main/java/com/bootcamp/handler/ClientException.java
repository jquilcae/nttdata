package com.bootcamp.handler;

import org.springframework.http.HttpStatus;

public class ClientException extends RuntimeException {

    private HttpStatus httpStatus;

    public ClientException(String message) {
        super(message);
    }

    public ClientException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
