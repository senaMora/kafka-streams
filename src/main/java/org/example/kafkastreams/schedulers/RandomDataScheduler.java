package org.example.kafkastreams.schedulers;

import lombok.RequiredArgsConstructor;
import org.example.kafkastreams.services.DataProducerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomDataScheduler {

    private final DataProducerService producerService;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void produce() {
        producerService.sendRandomData();
    }

}