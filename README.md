# Kafka Streams with Spring Boot

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Example](#example)
- [Example Output](#example-output)
- [Architecture Overview](#architecture-overview)
- [How to Run](#how-to-run)

## Overview

Kafka Streams is a Java library provided by Apache Kafka that allows you to build real-time applications and microservices that process data directly from Kafka topics. It enables processing of data in real-time directly from Kafka topics.

## Features

- **Real-time processing**
- **Stateless and stateful transformations**
- **Aggregation and windowing operations**
- **Fault-tolerance and scalability**

## Example

The provided example reads messages from an input topic, splits them into individual words, counts occurrences per word, and writes the results to an output topic.

```java
@Bean
public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
    KStream<String, String> stream = streamsBuilder.stream(
            inputTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

    log.info("consuming");

    KTable<String, Long> wordCounts = stream
            .mapValues((ValueMapper<String, String>) String::toLowerCase)
            .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
            .groupByKey(Grouped.with(STRING_SERDE, STRING_SERDE))
            .count();

    wordCounts.toStream().to(outputTopic);

    return stream;
}
```

## Example Output

`hello world hello` →

```
hello: 2
world: 1
```

## Architecture Overview

Below is a simple architecture diagram illustrating Kafka Streams workflow:

![Kafka Streams Diagram](https://github.com/senaMora/kafka-streams/blob/main/src/main/resources/images/overview.png)

## How to Run

1. Ensure Kafka is running locally.
2. Set topic configurations in `application.properties`.
3. Start your Spring Boot application.
4. Produce messages to your `input-topic`.
5. Consume the processed messages from `output-topic`.

---

© Your Project - Kafka Streams Example
