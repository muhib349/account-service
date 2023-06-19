package com.abc.bank.accountservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String saveAccountTopic;
    @Bean
    public NewTopic getTopic(){
        return TopicBuilder
                .name(saveAccountTopic)
                .partitions(2)
                .build();
    }
}
