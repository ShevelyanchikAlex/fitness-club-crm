package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailDetails;

/**
 * The EmailService provides the ability to send Email.
 *
 * @version 1.0.1
 */
public interface EmailService {
    /**
     * Sends an email using the provided email details.
     *
     * @param emailDetails The details of the email to be sent.
     */
    void sendEmail(EmailDetails emailDetails);
}
