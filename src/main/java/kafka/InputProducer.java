package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import travelling_salesman.Graph;
import travelling_salesman.TravellingSalesman;

public class InputProducer {

	public void runProducer(int recordsAmount) {
		Producer<String, String> producer = PropertiesCreator.createProducer();

		Graph graph = new Graph();
		
		for (int index = 0; index < graph.vertexAmount(); index++) {
			String value = String.valueOf(index);

			final ProducerRecord<String, String> record;
			record = new ProducerRecord<String, String>(KafkaConstants.INPUT_TOPIC, value);
			producer.send(record);
			
			System.out.println("Vertex " + index + " sent to INPUT_TOPIC");
		}
		
		readOutputConsumer();
		
		producer.close();
		System.out.println("All the records were created");
	}
	
	static void readOutputConsumer() {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.OUTPUT_TOPIC);

		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			
			if (1 == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords)
				System.out.println("Reading result from " + record.value());
			
		}
		consumer.close();
		System.out.println("All records from OUTPUT_TOPIC were read");
	}
	
}
