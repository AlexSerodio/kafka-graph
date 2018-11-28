package slave;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import master.KafkaConstants;
import master.PropertiesCreator;

public class Slave {

	private int delay;
	private final String BROKER_ADDRESS;
	private String name;
	
	Consumer<String, String> consumer;
	Producer<String, String> producerOutput;
	
	public Slave(String name, String brokerAddress, int delay) {
		this.BROKER_ADDRESS = brokerAddress;
		this.name = name;
		this.delay = delay;
		consumer = PropertiesCreator.createConsumer(KafkaConstants.INPUT_TOPIC, BROKER_ADDRESS);
		producerOutput = PropertiesCreator.createProducer(BROKER_ADDRESS);
	}
	
	public void run() {
		processVertices.start();
	}
	
	Thread processVertices = new Thread(){
		public void run(){
			
			Graph graph = new Graph();
			TravellingSalesman salesman;
			
			while (true) {
				final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
				
				for (ConsumerRecord<String, String> record : consumerRecords) {
					salesman = new TravellingSalesman(graph.getCosts());
					
					int startPoint = Integer.parseInt(record.value());
					salesman.findShortestPath(startPoint);
					int[] route = salesman.getPath();
					String result = graph.convertToString(route);
					
					System.out.println(name + " is processing vertex " + record.value());
					
					final ProducerRecord<String, String> newRecord;
					newRecord = new ProducerRecord<String, String>(KafkaConstants.OUTPUT_TOPIC, result);
					producerOutput.send(newRecord);
					System.out.println("Vertex were processed successfully: " + result);
					System.out.println("+-----------------------------------------------------------+");
					
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
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
}
