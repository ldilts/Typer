package demo;

import factories.AbstractFactory;
import interfaces.Client;
import model.SocketType;
import producer.FactoryProducer;

import java.io.IOException;

public class ClientDemoUDP {

    public static void main(String[] args) throws IOException {
    	
    	System.out.println("Cliente");
    	
        AbstractFactory clientFactory = FactoryProducer.getFactory(SocketType.CLIENT);

        int serverPort = 1234;
        String serverAddress = "localhost";
        
        Client client = clientFactory.getClient(SocketType.CLIENT_UDP, serverAddress, serverPort);
        
        boolean connected = client.connect();
        
        if(connected){
        	
        	for (int i = 0; i < 10 ; i++) {
				
        		String msg = "Teste";

				client.send(msg);

				String echo = client.receive();

				System.out.println("Echo: " + echo);
			}
            
        } else {
        	System.out.println("Error!");
        }
    }
}
