package chat;

import java.io.IOException;

import factories.AbstractFactory;
import interfaces.Server;
import model.SocketType;
import producer.FactoryProducer;
import threads.ConnectionListener;

public class ServerTCP {
	
	public static void main(String[] args) throws IOException {
			
			AbstractFactory serverFactory = FactoryProducer.getFactory(SocketType.SERVER);
			
			int serverPort = 1234;
				
			Server server = serverFactory.getServer(SocketType.SERVER_TCP, serverPort);
			
			Thread listenerConn = new Thread(new ConnectionListener(server));
			
			listenerConn.start();
	}
}
