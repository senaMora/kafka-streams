package org.example.kafkastreams.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class KafkaTexasTopicListener {

    /** READ FROM TEXAS KAFKA TOPIC **/
    @KafkaListener(
            topics = "${spring.kafka.topic.texassales}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void readRxClaimStream(@Payload String record) {

        if(record!=null && record.length()>0) {
            try {
                //DO ADDITIONAL PROCESSING WITH THIS FILTERED STREAM OF TEXAS SALES. FOR NOW JUST PRINTING IT OUT
                log.info("TEXAS TOPIC => {}", record);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}