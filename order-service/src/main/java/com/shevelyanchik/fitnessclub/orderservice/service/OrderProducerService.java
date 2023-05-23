package com.shevelyanchik.fitnessclub.orderservice.service;


import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The OrderProducerService provides the ability to send Message to Topic.
 *
 * @version 1.0.1
 */
public interface OrderProducerService {
    /**
     * Sends a message using the provided EmailEvent.
     *
     * @param event The EmailEvent object containing the message details.
     */
    void sendMessage(EmailEvent event);
}
