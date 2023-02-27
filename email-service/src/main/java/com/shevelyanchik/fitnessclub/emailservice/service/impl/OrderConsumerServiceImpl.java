package com.shevelyanchik.fitnessclub.emailservice.service.impl;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailDetails;
import com.shevelyanchik.fitnessclub.emailservice.dto.OrderEventDto;
import com.shevelyanchik.fitnessclub.emailservice.service.EmailService;
import com.shevelyanchik.fitnessclub.emailservice.service.OrderConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumerServiceImpl implements OrderConsumerService {

    private final EmailService emailService;
    @Value("${spring.mail.recipient}")
    private String recipient;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Override
    public void consume(OrderEventDto event) {
        EmailDetails emailDetails = buildEmailDetails(event);
        emailService.sendEmail(emailDetails);
        log.info(event.toString());
    }

    private EmailDetails buildEmailDetails(OrderEventDto event) {
        return EmailDetails.builder()
                .recipient(recipient)
                .subject(event.getMessage())
                .messageBody(event.toString())
                .build();
    }
}
