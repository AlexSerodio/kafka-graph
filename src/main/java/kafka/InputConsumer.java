package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import travelling_salesman.Graph;
import travelling_salesman.TravellingSalesman;

public class InputConsumer {

	public void runConsumer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC);
		Producer<String, String> producerOutput = PropertiesCreator.createProducer();
		
		Graph graph = new Graph();
		TravellingSalesman salesman = new TravellingSalesman(graph.getWeights());
		
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			
			if (consumerRecords.count() == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				
				// TODO - processamento do grafo sera feito aqui
				// String result = ProcessVertex.Double(record.value());
				
				
				System.out.print(name + " is processing vertex " + record.value());
				
				final ProducerRecord<String, String> newRecord;
				newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, "Vertex " + record.value() + " | Result " + "not yet");
				producerOutput.send(newRecord);		
				System.out.println(" | Result of " + record.value() + " was sent to output topic");
			}
		}
		consumer.close();
		producerOutput.close();
		System.out.println("All records from INPUT_TOPIC were read");
	}	
}
