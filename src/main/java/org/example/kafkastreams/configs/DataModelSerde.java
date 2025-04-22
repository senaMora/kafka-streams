package org.example.kafkastreams.configs;

import org.apache.kafka.common.serialization.Serdes;
import org.example.kafkastreams.dtos.DataModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class DataModelSerde extends Serdes.WrapperSerde<DataModel> {

    public DataModelSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(DataModel.class));
    }
}
