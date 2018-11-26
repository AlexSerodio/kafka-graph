package kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import travelling_salesman.Graph;

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
		
		producer.close();
		System.out.println("All the records were created");
	}
	
}
