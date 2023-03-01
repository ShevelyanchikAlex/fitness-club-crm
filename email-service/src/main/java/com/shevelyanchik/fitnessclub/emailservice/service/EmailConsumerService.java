package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The EmailConsumerService provides the ability to consume EmailEvents.
 *
 * @version 1.0.1
 */
public interface EmailConsumerService {
    /**
     * Consumes EmailEvent.
     *
     * @param event Email event
     */
    void consume(EmailEvent event);
}
