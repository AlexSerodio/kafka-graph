package kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class InputProducer {

	public void runProducer(int recordsAmount) {
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
	
}
