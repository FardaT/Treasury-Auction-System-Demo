package com.greenfox.treasuryauctionsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDetails {

    // https://stackoverflow.com/questions/25764459/spring-boot-application-properties-value-not-populating

    public static int expiration;
    public static float percentage;
    public static long max_amount;
    public static int multiple_of;
    public static float min_rate;
    public static float max_rate;

    @Value("${expiration}")
    public void setExpiration(int expiration) {ApplicationDetails.expiration = expiration;}
    @Value("${percentage}")
    public void setPercentage(float percentage) {
        ApplicationDetails.percentage = percentage;
    }
    @Value("${max_amount}")
    public void setMax_amount(long max_amount) {
        ApplicationDetails.max_amount = max_amount;
    }
    @Value("${multiple_of}")
    public void setMultiple_of(int multiple_of) {
        ApplicationDetails.multiple_of = multiple_of;
    }
    @Value("${min_rate}")
    public void setMin_rate(float min_rate) {
        ApplicationDetails.min_rate = min_rate;
    }
    @Value("${max_rate}")
    public void setMax_rate(float max_rate) {
        ApplicationDetails.max_rate = max_rate;
    }

    public ApplicationDetails() {
    }
}
