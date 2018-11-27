package producer;

import java.util.ArrayList;
import java.util.Random;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class InputProducer {

	private int delay;
	private final String BROKER_ADDRESS;
	private final int VERTEX_COUNT = 15;
	
	private Graph graph = new Graph();
	ArrayList<Integer> vertex = new ArrayList<>();
	
	public InputProducer(String brokerAddress, int delay) {
		this.BROKER_ADDRESS = brokerAddress;
		this.delay = delay;
		
		for(int i =0; i < VERTEX_COUNT; i++)
			vertex.add(i);
	}
	
	public void run() {
		createVertices.start();
		readOutputConsumer.start();
	}
	
	Thread createVertices = new Thread(){
		public void run(){
			Producer<String, String> producer = PropertiesCreator.createProducer(BROKER_ADDRESS);
			
			Random rand = new Random();
			
			for (int index = 0; index < VERTEX_COUNT; index++) {
				String value = String.valueOf(vertex.remove(rand.nextInt(vertex.size())));

				final ProducerRecord<String, String> record;
				record = new ProducerRecord<String, String>(KafkaConstants.INPUT_TOPIC, value);
				producer.send(record);
				
				System.out.println("Vertex " + index + " sent to INPUT_TOPIC");
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			producer.close();
			System.out.println("All the vertices were created");
	    }
	};
	
	Thread readOutputConsumer = new Thread(){
		public void run(){
			Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.OUTPUT_TOPIC, BROKER_ADDRESS);
			
			System.out.println("Producer started to read the outputs");
			
			while (true) {
				final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
							
				for (ConsumerRecord<String, String> record : consumerRecords) {
					int[] route = graph.convertToArray(record.value());
					graph.calculateShortestRoute(route);
					System.out.println("Reading route " + record.value());
					System.out.println("Shortest route: " + graph.convertToString(graph.getShortestRoute()) + 
							" | Cost: " + graph.getLowerCost());
					
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
	public void runProducer(int recordsAmount) {
		Producer<String, String> producer = PropertiesCreator.createProducer(BROKER_ADDRESS);
		
		Random rand = new Random();
		
		for (int index = 0; index < VERTEX_COUNT; index++) {
			String value = String.valueOf(vertex.remove(rand.nextInt(vertex.size())));

			final ProducerRecord<String, String> record;
			record = new ProducerRecord<String, String>(KafkaConstants.INPUT_TOPIC, value);
			producer.send(record);
			
			System.out.println("Vertex " + index + " sent to INPUT_TOPIC");
		}
		
		producer.close();
		System.out.println("All the records were created");

		//readOutputConsumer();
	}
	*/
	
	/*
	public void readOutputConsumer() {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.OUTPUT_TOPIC, BROKER_ADDRESS);
		
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
						
			for (ConsumerRecord<String, String> record : consumerRecords) {
				int[] route = graph.convertToArray(record.value());
				graph.calculateShortestRoute(route);
				System.out.println("Reading route " + record.value());
				System.out.println("Shortest route: " + graph.convertToString(graph.getShortestRoute()) + 
						" | Cost: " + graph.getLowerCost());
			}
			
		}
	}
	*/
	
	public void clearOutputConsumer() {
		Consumer<String, String> consumer = PropertiesCreator.createConsumer(KafkaConstants.OUTPUT_TOPIC, BROKER_ADDRESS);
		final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
		while(true) {	
			for (ConsumerRecord<String, String> record : consumerRecords) {	
				System.out.println(record.value());
			}
		}
		//System.out.println("OUTPUT_TOPIC is clear");
	}
		
}
