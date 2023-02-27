package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderEventDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducerServiceImpl implements OrderProducerService {

    private final NewTopic topic;
    private final KafkaTemplate<String, OrderEventDto> kafkaTemplate;

    @Override
    public void sendMessage(OrderEventDto event) {
        Message<OrderEventDto> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
