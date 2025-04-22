package org.example.kafkastreams.dtos;

import lombok.Data;

@Data
public class AggregateTotal {

    long count;
    double amount;

}