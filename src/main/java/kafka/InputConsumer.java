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
		TravellingSalesman salesman;
		
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			
			if (consumerRecords.count() == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				salesman = new TravellingSalesman(graph.getWeights());
				
				int startPoint = Integer.parseInt(record.value());
				salesman.findShortestPath(startPoint);
				int[] route = salesman.getRoute();
				String result = graph.convertToString(route);
				
				System.out.println(name + " is processing vertex " + record.value());
				
				final ProducerRecord<String, String> newRecord;
				newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, "Route for " + record.value() + ": " + result);
				producerOutput.send(newRecord);			
			}
		}
		consumer.close();
		producerOutput.close();
		System.out.println("All records from INPUT_TOPIC were read");
	}
	
	public void clearProducer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC);
		
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
				
			if (consumerRecords.count() == 0)
				break;
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				System.out.println(name + " is processing vertex " + record.value());
			}
		}
		consumer.close();
		System.out.println("All records from INPUT_TOPIC were read");
	}
}
