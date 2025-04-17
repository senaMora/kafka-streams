package org.example.kafkastreams.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.input.topic}")
    private String inputTopic;

    @Value("${kafka.output.topic}")
    private String outputTopic;

    @Bean
    public NewTopic createInputTopic() {
        return new NewTopic(inputTopic, 1, (short) 1); // 1 partition, replication factor 1
    }

    @Bean
    public NewTopic createOutputTopic() {
        return new NewTopic(outputTopic, 1, (short) 1); // 1 partition, replication factor 1
    }
}