package example_update;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Antes de executar é preciso:
 * 
 * 1. Baixar o kafka a partir do site oficial
 * 2. Va ate a pasta de instalacao e execute os seguintes comandos:
 * 
 * Para iniciar zookeeper e kafka
 * ./bin/zookeeper-server-start.sh config/zookeeper.properties
 * ./bin/kafka-server-start.sh config/server.properties
 * 
 * Para criar os topics
 * ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-plaintext-input
 * ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-wordcount-output --config cleanup.policy=compact
 * 
 * Para acessar os topics
 * ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input
 * ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
 */
public final class Consumer {

    public static void main(final String[] args) {

    	final Properties props = PopertiesCreator.createProperties();
    	
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, String> source = builder.stream(KafkaConstants.INPUT_TOPIC);
        final KTable<String, Long> counts = source
            .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split(" ")))
            .groupBy((key, value) -> value)
            .count();

        // counts.foreach((w, c) -> System.out.println("word: " + w + " -> " + c));
        
        // need to override value serde to Long type
        counts.toStream().to(KafkaConstants.OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (final Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}