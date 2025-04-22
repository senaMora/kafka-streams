package org.example.kafkastreams.services;

import lombok.RequiredArgsConstructor;
import org.example.kafkastreams.dtos.DataModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataProducerService {

    private final KafkaTemplate<String, DataModel> kafkaTemplate;

    @Value("${spring.kafka.topic.demo}")
    private String topic;

    private final Random random = new Random();

    private final List<String> makes = List.of("Toyota", "Ford", "BMW", "Tesla");
    private final List<String> models = List.of("Corolla", "F-150", "3 Series", "Model 3");
    private final List<String> states = List.of("CA", "TEXAS", "NY", "FL");
    private final List<String> dealerNames = List.of("AutoWorld", "BestCars", "CarHub");

    public void sendRandomData() {
        DataModel data = new DataModel(
                UUID.randomUUID().toString(),
                getRandom(makes),
                getRandom(models),
                String.valueOf(2010 + random.nextInt(15)),
                Instant.now().toString(),
                String.valueOf(1000 + random.nextInt(5)),
                getRandom(dealerNames),
                getRandom(states),
                String.valueOf(10000 + random.nextInt(50000))
        );

        kafkaTemplate.send(topic, data.getTransactionId(), data);
    }

    private String getRandom(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }
}