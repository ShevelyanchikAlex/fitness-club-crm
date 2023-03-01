package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailDetails;

/**
 * The EmailService provides the ability to send Email.
 *
 * @version 1.0.1
 */
public interface EmailService {
    /**
     * Sends Email.
     *
     * @param emailDetails Email Details
     */
    void sendEmail(EmailDetails emailDetails);
}
