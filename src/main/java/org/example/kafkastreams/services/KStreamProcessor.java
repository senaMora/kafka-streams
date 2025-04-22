package org.example.kafkastreams.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.example.kafkastreams.dtos.DataModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KStreamProcessor {

    @Value("${spring.kafka.topic.texassales}")
    private String texassalestopic;

    public void process(KStream<String, DataModel> stream){
        log.info("Processing data stream ================");

        //KSTREAM FILTER: Filter the Stream to get Texas sales into a new Texas Topic
        stream.filter(
                (key, object) ->
                        object != null && object.getState() != null && object.getState()
                                .trim()
                                .equalsIgnoreCase("TEXAS")
                ).to(texassalestopic);

    }
}