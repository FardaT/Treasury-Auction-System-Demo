package com.greenfox.treasuryauctionsystem;

import com.greenfox.treasuryauctionsystem.services.TestService;
import com.greenfox.treasuryauctionsystem.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TreasuryAuctionSystemApplication implements CommandLineRunner {

  private EmailService emailService;
  private TestService testService;

  @Autowired
  public TreasuryAuctionSystemApplication(EmailService emailService, TestService testService) {
    this.emailService = emailService;
    this.testService = testService;
  }

  public static void main(String[] args) {
    SpringApplication.run(TreasuryAuctionSystemApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    //emailService.sendSimpleMessage("szecsi.istvan@gmail.com", "Hi, from the Mighty Rooster Team", "This is a test email template for your treasury application");
    //testService.fillDatabase();
  }
}
