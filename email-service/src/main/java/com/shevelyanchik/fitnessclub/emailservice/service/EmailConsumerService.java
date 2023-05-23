package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The EmailConsumerService provides the ability to consume EmailEvents.
 *
 * @version 1.0.1
 */
public interface EmailConsumerService {
    /**
     * Consumes an email event and performs the necessary actions.
     *
     * @param event The email event to be consumed.
     */
    void consume(EmailEvent event);
}
