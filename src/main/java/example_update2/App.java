package example_update2;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import travelling_salesman.ProcessVertex;

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
public final class App {
	
	public static void main(final String[] args) {
		runProducer(1000);
		runConsumer("Consumer 1");
		runOutputConsumer("Output 1");
	}
	
	static void runProducer(int recordsAmount) {
		Producer<String, String> producer = PropertiesCreator.createProducer();

		for (int index = 0; index < recordsAmount; index++) {
			final ProducerRecord<String, String> record;
			String value = String.valueOf(index);
			record = new ProducerRecord<String, String>(KafkaConstants.INPUT_TOPIC, value);
			producer.send(record);
			System.out.println("Vertex " + index + " sent");
		}
		producer.close();
		System.out.println("All the records were created");
	}
	
	static void runConsumer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC);
		Producer<String, String> producerOutput = PropertiesCreator.createProducer();
		
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			
			if (consumerRecords.count() == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				
				// TODO - processamento do grafo sera feito aqui
				String result = ProcessVertex.Double(record.value());
				
				System.out.println(name + " is processing vertex " + record.value());
				
				final ProducerRecord<String, String> newRecord;
				newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, "Vertex " + record.value() + " | Result " + result);
				producerOutput.send(newRecord);		
				System.out.println("Result of " + record.value() + " was sent to output topic");
			}
		}
		consumer.close();
		producerOutput.close();
		System.out.println("All records from INPUT_TOPIC were read");
	}
	
	static void runOutputConsumer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.OUTPUT_TOPIC);

		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			
			if (consumerRecords.count() == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				System.out.println(name + " is reading result from " + record.value());
			}
		}
		consumer.close();
		System.out.println("All records from OUTPUT_TOPIC were read");
	}
}