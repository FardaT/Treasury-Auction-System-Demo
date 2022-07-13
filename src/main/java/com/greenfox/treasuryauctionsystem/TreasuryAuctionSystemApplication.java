package com.greenfox.treasuryauctionsystem;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.services.TestService;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication
public class TreasuryAuctionSystemApplication implements CommandLineRunner {

    private TestService testService;

    @Autowired
    public TreasuryAuctionSystemApplication(TestService testService) {
        this.testService = testService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TreasuryAuctionSystemApplication.class, args);
    }

    // Hashing password
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SessionScope
    public Auction tempAuction() {
        return new Auction();
    }

    @Override
    public void run(String... args) throws Exception {
        //emailService.sendSimpleMessage("szecsi.istvan@gmail.com", "Hi, from the Mighty Rooster Team", "This is a test email template for your treasury application");
        //testService.seedDatabase();

        testService.fillDatabase();
        // System.out.println(ApplicationDetails.expiration);
    }
}
