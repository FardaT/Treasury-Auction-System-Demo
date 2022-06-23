package com.greenfox.treasuryauctionsystem.utils;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

  @Value("${GMAIL_APP_PASSWORD}")
  private String gmailAppPassword;

  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("mightyroosterteam@gmail.com");
    mailSender.setPassword(gmailAppPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    props.put("mail.smtp.ssl.trust", "*");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    return mailSender;
  }

  public void sendSimpleMessage(
      String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("mightyroosterteam@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    getJavaMailSender().send(message);
  }

  public void sendHtmlMessage(String to, String subject, String text) throws MessagingException {
    MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    helper.setFrom("mightyroosterteam@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);
    getJavaMailSender().send(mimeMessage);
  }
}
