# Kafka Streams with Spring Boot

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Example](#example)
- [Example Output](#example-output)
- [Architecture Overview](#architecture-overview)
- [How to Run](#how-to-run)

## Project Structure

There are **two separate projects** in this repository:

1. **Basic Kafka Streams App** – A minimal example covering fundamental stream operations. **This README file includes details specifically for this basic approach only.**
2. **Kafka Streams Demo App** – A more complete simulation based on [this Medium tutorial](https://medium.com/@tobintom/introducing-kafka-streams-with-spring-boot-be1d6f7f3b76), including state stores, aggregation, and realistic data flow.

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

## Sample Output from Kafka Streams Application

The image below shows the result of processing messages with Kafka Streams. The input topic contains simple sentences, which the stream splits into words and counts. The resulting word counts are written to the output topic.

- Input:
    - `This is the kafka is a statement`
    - `This is a cat`
- Output:
    - Word counts like: `this: 2`, `is: 3`, `a: 2`, `cat: 1`, etc.

![Kafka Streams Word Count Result](https://github.com/senaMora/kafka-streams/blob/main/src/main/resources/images/results.png)

## How to Run

1. Ensure Kafka is running locally.
2. Set topic configurations in `application.properties`.
3. Start your Spring Boot application.
4. Produce messages to your `input-topic`.
5. Consume the processed messages from `output-topic`.

---

© senaMora Projects - Kafka Streams Example
