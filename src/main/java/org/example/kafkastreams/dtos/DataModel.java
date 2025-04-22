package org.example.kafkastreams.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataModel {

    String transactionId;
    String make;
    String model;
    String year;
    String saleTimestamp;
    String dealerId;
    String dealerName;
    String state;
    String price;

}