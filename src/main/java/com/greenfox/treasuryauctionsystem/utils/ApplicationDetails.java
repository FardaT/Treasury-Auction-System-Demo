package com.greenfox.treasuryauctionsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDetails {

    public static int expiration;

    @Value("${expiration}")
    public void setProp(int expiration) {
        ApplicationDetails.expiration = expiration;
    }

    public ApplicationDetails() {
    }
}
