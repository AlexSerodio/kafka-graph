package example_update;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

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
public final class Producer {

	public static String[] vertices = {
		"2 3 9",
		"4 9 5",
		"3 0 6",
		"4 1 7",
		"5 3 8",
		"7 9 2",
		"8 45 53",
		"9 12 3"
	};
	
	public static int i = 0;
	
    public static void main(final String[] args) {

    	final Properties props = PopertiesCreator.createProperties();
    	
        final StreamsBuilder builder = new StreamsBuilder();
        
        final KTable<String, Long> counts = (KTable<String, Long>) new KeyValue(vertices[i], i);
        
        i = (i == vertices.length) ? i+1 : 0;
        
        // need to override value serde to Long type
        counts.toStream().to(KafkaConstants.INPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

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