package com.greenfox.treasuryauctionsystem.utils;

import java.util.Date;
import java.util.UUID;

public class Utility {

    // generate random string for email confirmation and forgot password
    // https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string/41762#41762
    public static String generateString() {
        return UUID.randomUUID().toString();
    }

    // set exparation
    public static Date setExpiration(int minutes) {
        return new Date(System.currentTimeMillis() + (long) minutes * 60 * 1000);
    }

    // https://stackoverflow.com/questions/5289849/how-do-i-send-html-email-in-spring-mvc
    public static String setConfirmationEmailText(String username, String activationToken) {

        String href = "http://localhost:8080/confirm_token?activationToken=" + activationToken;

        return "<p>Dear <b>" + username + "</b>!</p>" +
                "<p>You have successfully registered your account in our auction system.</p>" +
                "<p>In order to activate your account please click on the link below:</p>" +
                "<a href=" + href + ">Click here</a>" +
                "<p>Regards, US Treasury</p>";
    }
}
