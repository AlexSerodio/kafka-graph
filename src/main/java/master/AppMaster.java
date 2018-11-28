package master;

public final class AppMaster {
	
	public static void main(final String[] args) {
		
		Master master = new Master(KafkaConstants.KAFKA_BROKERS, 3000);
		master.run();
		
	}
}