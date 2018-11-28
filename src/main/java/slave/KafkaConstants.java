package slave;

public abstract class KafkaConstants {
	
	public static final String CLIENT_ID = "client1";
	public static final String GROUP_ID_CONFIG = "streams-wordcount";
	public static final boolean ENABLE_AUTO_COMMIT = true;
	public static final Integer AUTO_COMMIT_INTERVALS = 100;
	public static final String OFFSET_RESET_EARLIER = "earliest";
	public static final Integer MAX_POLL_RECORDS = 1;
	
}

/**
 * Antes de executar é preciso realizar os seguintes passos:
 * 
 * 1. Baixar o kafka a partir do site oficial
 * 2. Ir ate a pasta de instalacao e executar os seguintes comandos:
 * 
 * Para iniciar zookeeper e kafka
 * ./bin/zookeeper-server-start.sh config/zookeeper.properties
 * ./bin/kafka-server-start.sh config/server.properties
 * 
 * Para criar os topics (caso ja nao tenham sido criados)
 * ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-plaintext-input
 * ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-wordcount-output --config cleanup.policy=compact
*/