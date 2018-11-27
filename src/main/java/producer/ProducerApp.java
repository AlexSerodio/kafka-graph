package producer;

public final class ProducerApp {
	
	public static void main(final String[] args) {
		
		String ip = (args.length == 0) ? KafkaConstants.KAFKA_BROKERS : args[0];
		
		InputProducer producer = new InputProducer(ip, 3000);
		producer.run();
		
	}
}