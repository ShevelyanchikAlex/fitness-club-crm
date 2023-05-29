package com.shevelyanchik.fitnessclub.newsservice.service;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;

/**
 * The NewsProducerService provides the ability to send Message to Topic.
 *
 * @version 1.0.1
 */
public interface NewsProducerService {

    /**
     * Sends a Message using the provided EmailEvent.
     *
     * @param event The EmailEvent object containing the message details.
     */
    void sendMessage(EmailEvent event);

}
