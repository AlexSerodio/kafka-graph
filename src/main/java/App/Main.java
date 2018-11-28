package App;

import master.Master;
import slave.Slave;

public class Main {

	public static void main(String[] args) {
		
		String ip = "localhost:9092";
		
		Slave consumer = new Slave("Consumer 1", ip, 3000);
		Master producer = new Master(ip, 3000);
		
		producer.run();
		consumer.run();

	}

}