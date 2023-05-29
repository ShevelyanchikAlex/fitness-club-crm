package com.shevelyanchik.fitnessclub.newsservice.service.impl;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import com.shevelyanchik.fitnessclub.newsservice.service.NewsProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsProducerServiceImpl implements NewsProducerService {

    private final NewTopic topic;
    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    @Override
    public void sendMessage(EmailEvent event) {
        Message<EmailEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }

}
