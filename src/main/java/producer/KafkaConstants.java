package producer;

public abstract class KafkaConstants {
	
	public static final String KAFKA_BROKERS = "localhost:9092";
	public static final String CLIENT_ID = "client1";
	public static final String INPUT_TOPIC = "streams-plaintext-input";
	public static final String OUTPUT_TOPIC = "streams-wordcount-output";
	public static final String GROUP_ID_CONFIG = "streams-wordcount";
	public static final boolean ENABLE_AUTO_COMMIT = true;
	public static final Integer AUTO_COMMIT_INTERVALS = 100;
	public static final String OFFSET_RESET_LATEST = "latest";
	public static final String OFFSET_RESET_EARLIER = "earliest";
	public static final Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
	public static final Integer MAX_POLL_RECORDS = 1;
	
}
