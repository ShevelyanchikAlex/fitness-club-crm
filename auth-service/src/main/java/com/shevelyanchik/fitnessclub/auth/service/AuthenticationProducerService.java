package com.shevelyanchik.fitnessclub.auth.service;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The AuthenticationProducerService provides the ability to send Message to Topic.
 *
 * @version 1.0.1
 */
public interface AuthenticationProducerService {
    /**
     * Sends a message using the provided EmailEvent.
     *
     * @param event The EmailEvent object containing the message details.
     */
    void sendMessage(EmailEvent event);
}
