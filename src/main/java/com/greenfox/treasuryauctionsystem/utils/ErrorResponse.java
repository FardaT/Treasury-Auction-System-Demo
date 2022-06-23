package com.greenfox.treasuryauctionsystem.utils;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    // FIELDS
    private String errorMessage;
    private HttpStatus errorCode;

    // CONSTRUCTOR
    public ErrorResponse(String errorMessage, HttpStatus errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    // GETTERS
    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    // SETTERS
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
