package org.example.kafkastreams.services;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.example.kafkastreams.dtos.AggregateTotal;
import org.example.kafkastreams.configs.AggregateTotalSerdes;
import org.example.kafkastreams.dtos.DataModel;
import org.springframework.stereotype.Component;

@Component
public class KTableProcessor {

    //KTABLE STATE: Create a KTable for State of sales per dealer
    public void process(KStream<String, DataModel> stream){

        //Create a new KeyValue Store
        KeyValueBytesStoreSupplier dealerSales = Stores.persistentKeyValueStore(
                "dealer-sales-amount");

        KGroupedStream<String, Double> salesByDealerId = stream
                .map((key, sales) -> new KeyValue(sales.getDealerId(), Double.parseDouble(sales.getPrice())))
                .groupByKey();

        KTable<String, AggregateTotal> dealerAggregate = salesByDealerId.aggregate(() -> new AggregateTotal(),
                (k,v,aggregate) -> {
                    aggregate.setCount(aggregate.getCount()+1);
                    aggregate.setAmount(aggregate.getAmount()+v);
                    return aggregate;
                }, Materialized.with(Serdes.String(),new AggregateTotalSerdes()));

        final KTable<String, Double> dealerTotal =
                dealerAggregate.mapValues(value -> value.getAmount(),Materialized.as(dealerSales));
    }
}