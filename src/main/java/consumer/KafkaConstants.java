package consumer;

public abstract class KafkaConstants {
	
	//public static final String KAFKA_BROKERS = "localhost:9092";
	public static final String CLIENT_ID = "client1";
	public static final String INPUT_TOPIC = "streams-plaintext-input";
	public static final String OUTPUT_TOPIC = "streams-wordcount-output";
	public static final String GROUP_ID_CONFIG = "streams-wordcount";
	public static final boolean ENABLE_AUTO_COMMIT = true;
	public static final Integer AUTO_COMMIT_INTERVALS = 100;
	public static final String OFFSET_RESET_LATEST = "latest";
	public static final String OFFSET_RESET_EARLIER = "earliest";
	public static final Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
	public static final Integer MAX_POLL_RECORDS = 1;
	
}

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
 * ./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic streams-plaintext-input
 * 
 * Para acessar os topics
 * ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input
 * ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
 */