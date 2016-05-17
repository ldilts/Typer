package demo;

import factories.AbstractFactory;
import interfaces.Client;
import interfaces.Server;
import model.SocketType;
import producer.FactoryProducer;

import java.io.IOException;

public class ServerDemoUDP {

    public static void main(String[] args) throws IOException {

		System.out.println("Servidor");
		
		AbstractFactory serverFactory = FactoryProducer.getFactory(SocketType.SERVER);
		
		int serverPort = 1234;
			
		Server server = serverFactory.getServer(SocketType.SERVER_UDP, serverPort);
		
		Client toClient = server.hear();
		Client toClient2 = server.hear();
		
		int counter = 0;
		
		while(true){
			String message = toClient.receive();
			String message2 = toClient2.receive();
			
			System.out.println("Servidor recebeu: " + message);
			System.out.println("Servidor recebeu: " + message2);
			
			toClient.send(message.toUpperCase() + " " + counter++);
			toClient2.send(message2.toUpperCase() + " " + counter++);
		}
    }
}
