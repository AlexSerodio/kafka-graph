package consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import producer.KafkaConstants;
import producer.PropertiesCreator;

public class InputConsumer {

	private int delay;
	private final String BROKER_ADDRESS;
	private String name;
	
	public InputConsumer(String name, String brokerAddress, int delay) {
		this.BROKER_ADDRESS = brokerAddress;
		this.name = name;
		this.delay = delay;
	}
	
	public void run() {
		processVertices.start();
	}
	
	Thread processVertices = new Thread(){
		public void run(){
			Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC, BROKER_ADDRESS);
			Producer<String, String> producerOutput = PropertiesCreator.createProducer(BROKER_ADDRESS);
			
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
					newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, result);
					producerOutput.send(newRecord);
					System.out.println("Vertex were processed sucessifully: " + result);
					
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			consumer.close();
			producerOutput.close();
			System.out.println("All records from INPUT_TOPIC were read");
	    }
	};
	
	/*
	public void runConsumer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC, BROKER_ADDRESS);
		Producer<String, String> producerOutput = PropertiesCreator.createProducer(BROKER_ADDRESS);
		
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
				newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, result);
				producerOutput.send(newRecord);
				System.out.println("Vertex were processed sucessifully: " + result);
			}
		}
		consumer.close();
		producerOutput.close();
		System.out.println("All records from INPUT_TOPIC were read");
	}
	*/
	
	public void clearProducer(String name) {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC, BROKER_ADDRESS);
		
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
