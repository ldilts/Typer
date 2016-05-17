package demo;

import java.io.IOException;

import factories.AbstractFactory;
import interfaces.Client;
import interfaces.Server;
import model.SocketType;
import producer.FactoryProducer;

public class ServerDemoTCP {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Servidor");
		
		AbstractFactory serverFactory = FactoryProducer.getFactory(SocketType.SERVER);
		
		int serverPort = 1234;
			
		Server server = serverFactory.getServer(SocketType.SERVER_TCP, serverPort);
		
		Client toClient = server.hear();
		
		int counter = 0;
		
		while(true){
			String message = toClient.receive();
			
			System.out.println("Servidor recebeu: " + message);
			
			toClient.send(message.toUpperCase() + " " + counter++);
		}
	}
	
}
