package com.shevelyanchik.fitnessclub.auth.config;

import com.shevelyanchik.fitnessclub.kafkaconfig.topic.TopicName;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AuthenticationTopicConfig {

    @Value(TopicName.EMAIL_TOPIC)
    private String topicName;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).build();
    }
}
