package com.greenfox.treasuryauctionsystem.exceptions;

public class TokenExpiredException extends RuntimeException {
    // CONSTRUCTOR
    public TokenExpiredException(String message) {
        super(message);
    }
}
