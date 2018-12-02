package slave;

public final class AppSlave {
	
	public static void main(final String[] args) {
		
		Slave slave = new Slave("Consumer 1", KafkaConstants.KAFKA_BROKERS, 3000);
		slave.run();
		
	}
}