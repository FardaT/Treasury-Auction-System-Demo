package com.greenfox.treasuryauctionsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDetails {

    // https://stackoverflow.com/questions/25764459/spring-boot-application-properties-value-not-populating

    public static int expiration;

    @Value("${expiration}")
    public void setProp(int expiration) {
        ApplicationDetails.expiration = expiration;
    }

    public ApplicationDetails() {
    }
}
