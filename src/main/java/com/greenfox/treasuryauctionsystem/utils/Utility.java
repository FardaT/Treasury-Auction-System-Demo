package com.greenfox.treasuryauctionsystem.utils;

import java.time.LocalDateTime;
import java.util.UUID;

public class Utility {

    // FIELDS

    // generate random string for email confirmation and forgot password
    // https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string/41762#41762
    public static String generateString() {
        return UUID.randomUUID().toString();
    }

    // set expiration
    public static LocalDateTime setExpiration(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes);
    }

    // set confirmation email text
    // https://stackoverflow.com/questions/5289849/how-do-i-send-html-email-in-spring-mvc
    public static String setConfirmationEmailText(String username, String activationToken) {

        String href = "http://localhost:8080/confirm_token?activationToken=" + activationToken;

        return "<p>Dear <b>" + username + "</b>!</p>" +
                "<p>You have successfully registered your account in our auction system.</p>" +
                "<p>In order to activate your account please click on the link below:</p>" +
                "<a href=" + href + ">Click here</a>" +
                "<p>Regards, US Treasury</p>";
    }

    // password checking (regex)
    // https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    // (?=.*[0-9]) a digit must occur at least once
    // (?=.*[a-z]) a lower case letter must occur at least once
    // (?=.*[A-Z]) an upper case letter must occur at least once
    // (?=.*[@#$%^&+=]) a special character must occur at least once
    // (?=\\S+$) no whitespace allowed in the entire string
    // .{8,} at least 8 characters
    public static boolean validatePassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }
}
