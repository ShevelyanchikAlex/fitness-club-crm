package com.shevelyanchik.fitnessclub.emailservice.service.impl;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailDetails;
import com.shevelyanchik.fitnessclub.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(EmailDetails emailDetails) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetails.getRecipient());
        mailMessage.setText(emailDetails.getMessage());
        mailMessage.setSubject(emailDetails.getSubject());
        javaMailSender.send(mailMessage);
    }
}
