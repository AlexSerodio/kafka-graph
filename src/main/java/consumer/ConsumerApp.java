package consumer;

import producer.KafkaConstants;

public final class ConsumerApp {
	
	public static void main(final String[] args) {
		
		String ip = (args.length == 0) ? KafkaConstants.KAFKA_BROKERS : args[0];
		
		InputConsumer consumer = new InputConsumer("Consumer 1", ip, 3000);
		consumer.run();
		
	}
}