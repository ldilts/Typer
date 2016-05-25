package controller;

import java.io.IOException;

import factories.AbstractFactory;
import interfaces.Client;
import model.Addresses;
import model.ClientMatch;
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
	
	private ClientMatch match;
	private ClientGameView view;
	
	private Client client;

	public ClientGameController(ClientMatch match, ClientGameView view) {
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
				
				boolean receivedId = false;
				boolean updatedMatch = false;

				// Wait for start match message
				while(true){
					String message = client.receive();
					
					if (!receivedId) {
						if (MessageMaker.isPlayerIdMessage(message)) {
							receivedId = true;
							String currentId = MessageMaker.getMessage(message);
							CurrentPlayer.getInstance().setId(currentId);
							
							System.out.println("Received id: " + currentId);
							
							match.addPlayer(currentId, currentUsername);
						}
					} else if (!updatedMatch) {
						if (MessageMaker.isUpdateMatchMessage(message)) {
							updatedMatch = true;
							String statusString = MessageMaker.getMessage(message);
							match.updateMatch(statusString);
						}
					} else {
						if (MessageMaker.isStartMatchMessage(message)) {
							break;
						}
					}

				}
				
				view.startMatch();
				MatchThread matchThread = new MatchThread();
				matchThread.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class MatchThread extends Thread {
		
		public void run() {
			while(true) {
				try {
					String message = client.receive();
					
					if (MessageMaker.isUpdateMatchMessage(message)) {
						String matchStatus = MessageMaker.getUpdateMatchMessage(message);
						match.updateMatch(matchStatus);
						
						view.updateMatch();
						
					} else if (MessageMaker.isCompleteMatchMessage(message)) {
						String matchStatus = MessageMaker.getUpdateMatchMessage(message);
						match.updateMatch(matchStatus);
						
						view.updateMatch();
						
						String winnerUsername = match.getWinner();
						
						view.completeMatch(winnerUsername);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
