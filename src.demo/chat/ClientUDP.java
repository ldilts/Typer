package chat;

import java.io.IOException;

import factories.AbstractFactory;
import interfaces.Client;
import model.SocketType;
import producer.FactoryProducer;
import threads.WriteThread;
import threads.ReadThread;

public class ClientUDP {
	public static void main(String[] args) throws IOException {
	        
	    	System.out.println("Cliente");
	    	
	        AbstractFactory clientFactory = FactoryProducer.getFactory(SocketType.CLIENT);
	
	        int serverPort = 1234;
	        String serverAddress = "localhost";
	        
	        Client client = clientFactory.getClient(SocketType.CLIENT_UDP, serverAddress, serverPort);
	        
	        client.connect();
	        
	        Thread threadIn = new Thread(new ReadThread(client));
			Thread threadOut = new Thread(new WriteThread(client));
			
			threadIn.start();
			threadOut.start();
	}
}
