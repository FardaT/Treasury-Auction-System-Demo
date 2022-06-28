package com.greenfox.treasuryauctionsystem.exceptions;

public class AppUserNotFoundException extends RuntimeException {
    // CONSTRUCTOR
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
