package controller;

import java.io.IOException;

import factories.AbstractFactory;
import interfaces.Client;
import model.Addresses;
import model.CurrentPlayer;
import model.MessageMaker;
import model.Ports;
import model.ServerMatch;
import model.SocketType;
import producer.FactoryProducer;
import threads.ReadThread;
import threads.WriteThread;
import view.ClientGameView;

public class ClientGameController {
	
	private ServerMatch match;
	private ClientGameView view;
	
	private Client client;

	public ClientGameController(ServerMatch match, ClientGameView view) {
		super();
		this.match = match;
		this.view = view;
		
		AbstractFactory clientFactory = FactoryProducer.getFactory(SocketType.CLIENT);
        this.client = clientFactory.getClient(SocketType.CLIENT_TCP, 
        		Addresses.SERVER_ADDRESS, Ports.SERVER_PORT);
        
		this.startNewMatch();
	}
	
	/*
	 * Helper Methods
	 */
	
	private void startNewMatch() {
		// Request username from user and save into Singleton instance
		String currentUsername = view.startNewMatch();
		CurrentPlayer.getInstance().setUsername(currentUsername);
		
		try {
			// Connect to server
			boolean connectedToServer = client.connect();
			
			if (connectedToServer) {
				// Send username
				String usernameMessage = MessageMaker.getUsernameMessage(currentUsername);
				client.send(usernameMessage);

				// Wait for start match message
				while(true){
					String message = client.receive();
					
					if (MessageMaker.isStartMatchMessage(message)) {
						break;
					}
				}
				
				view.startMatch();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
