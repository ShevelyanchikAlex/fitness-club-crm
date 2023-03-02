package com.shevelyanchik.fitnessclub.orderservice.service;


import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The OrderProducerService provides the ability to send Message to Topic.
 *
 * @version 1.0.1
 */
public interface OrderProducerService {
    /**
     * Sends Message to Topic.
     *
     * @param event Email event
     */
    void sendMessage(EmailEvent event);
}
