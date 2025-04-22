package org.example.kafkastreams.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Slf4j
@Configuration
public class KafkaStreamsConfigDemo {

    @Value("${kafka.input.topic}")
    private String inputTopic;

    @Value("${kafka.output.topic}")
    private String outputTopic;

    private static final Serde<String> STRING_SERDE = Serdes.String();
    private static final Serde<Long> LONG_SERDE = Serdes.Long();


    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {

        KStream<String, String> stream = streamsBuilder.stream(
                inputTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

        log.info("consuming");
//        stream
//                .mapValues((ValueMapper<String, String>) String::toUpperCase)
//                .to(outputTopic);


//        KTable<String, Long> wordCounts = stream
//                .mapValues((ValueMapper<String, String>) String::toLowerCase)
//                .mapValues(value -> value.toUpperCase())
//                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
//                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
//                .count();



        KTable<String, Long> wordCounts = stream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+"))) // split into words
                .map((key, word) -> KeyValue.pair(word, word))                             // set word as new key
                .groupByKey(Grouped.with(STRING_SERDE, STRING_SERDE))                      // group by word
                .count(Materialized.with(STRING_SERDE, LONG_SERDE));                       // count

        wordCounts
                .toStream()
                .map((word, count) -> KeyValue.pair(word, count.toString()))              // convert count to string
                .to(outputTopic, Produced.with(STRING_SERDE, STRING_SERDE));              // write to output topic

        return stream;
    }
}
