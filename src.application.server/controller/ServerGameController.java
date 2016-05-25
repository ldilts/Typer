package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import factories.AbstractFactory;
import interfaces.Client;
import interfaces.Server;
import model.ServerMatch;
import model.MessageMaker;
import model.Ports;
import model.SocketType;
import model.WaitlistPlayer;
import producer.FactoryProducer;
import view.ServerGameView;

public class ServerGameController {

	private ServerMatch match;
	private ServerGameView view;

	private Server server;
	private HashMap<String, Client> clients;
	
	static Semaphore matchMutex = new Semaphore(1);
//	private ArrayList<WaitlistPlayer> waitlist;
	private ArrayList<PlayerThread> playerThreads;

	/*
	 * Public Constructors
	 */

	public ServerGameController(ServerMatch match, ServerGameView view) {
		super();
		this.match = match;
		this.view = view;

		AbstractFactory serverFactory = FactoryProducer.getFactory(SocketType.SERVER);
		this.server = serverFactory.getServer(SocketType.SERVER_TCP, Ports.SERVER_PORT);

		clients = new HashMap<String, Client>();

		this.startNewMatch();
	}

	/*
	 * Helper Methods
	 */

	private void startNewMatch() {
		// Get random words
		match.setWords(generateWords());

		// Wait for players to connect
		for (int i = 0; i < ServerMatch.MAX_PLAYERS; i++) {
			try {
				Client client = server.hear();
				String message = client.receive();

				if (MessageMaker.isUsernameMessage(message)) {
					String username = MessageMaker.getMessage(message);
					
					// Add player to match
					String id = match.addPlayer(username);
					
					// Send client their id
					String idMessage = MessageMaker.getPlayerIdMessage(id);
					client.send(idMessage);

					// Save client reference
					clients.put(id, client);
					
					view.displayMessage("Added " + username + " to match. " + (i + 1) + " of " + ServerMatch.MAX_PLAYERS
							+ " players.");
				} else {
					i--; // Try to acquire another client
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (match.isMatchReady) {
			
			playerThreads = new ArrayList<PlayerThread>();

			// Start player Threads
			for (HashMap.Entry<String, Client> entry : clients.entrySet()) {
				Client client = entry.getValue();
				String id = entry.getKey();

				PlayerThread playerThread = new PlayerThread(id, match.getUsernameFromId(id), client);
				playerThreads.add(playerThread);
				playerThread.start();
			}
			
			while (true) {
				if (match.getWinner() != null) {
					break;
				}
			}
			
			// TODO começar de novo

		} else {
			view.displayMessage("ERROR: Couldn't start match.");
		}
	}

	private String[] generateWords() {
		// TODO Generate random word array
		return new String[] { "one", "two", "three" };
	}

	public void updateView() {
	}

	/*
	 * Multi-threading
	 */
//	private class MatchThread extends Thread {
//		
//		public void run() {
//			while(true){
//				try {
//					Client client = server.hear();
//					String message = client.receive();
//					
//					
//					if (MessageMaker.isUsernameMessage(message)) {
//						String username = MessageMaker.getMessage(message);
//						
//						mutex.acquire();
//						WaitlistPlayer waitlistPlayer = new WaitlistPlayer(client, username);
//						waitlist.add(waitlistPlayer);
//						mutex.release();
//					}
//					
//				} catch (IOException | InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}

	private class PlayerThread extends Thread {
		
		private String id;
		private String username;
		private Client client;
		
        public PlayerThread(String id, String username, Client client) {
        	this.id = id;
            this.username = username;
            this.client = client;
        }
		
		public void run() {
			try {
				// Notify all clients that the match has started
				String updateMatchMessage = MessageMaker.getUpdateMatchMessage(match.getStatus());
				String startMatchMessage = MessageMaker.getStartMatchMessage();
				client.send(updateMatchMessage);
				client.send(startMatchMessage);
				
				while(true) {
					try {
						String message = this.client.receive();
						view.displayMessage("		" + username + ": " + message);
						
						if (match.getWinner() != null) {
							break;
						}
						
						// If a player levels up
						if (MessageMaker.isLevelUpMessage(message)) {
							int level = Integer.parseInt(MessageMaker.getMessage(message)); 
							
							// Notify other players of updated level
							matchMutex.acquire();
							
							// Has anyone won already?
							if (match.getWinner() == null) {
								// Update user level
								match.updatePlayerLevel(id, level);
								
								String matchStatusMessage = "";
								
								// Any winners?
								if (match.getWinner() != null) {
									// There's a winner!
									// Get match status string
									matchStatusMessage = MessageMaker.getCompleteMatchMessage(match.getStatus());
								} else {
									// No winner yet
									// Get match status string
									matchStatusMessage = MessageMaker.getUpdateMatchMessage(match.getStatus());
								}
								
								// Send match update to all clients
								for (HashMap.Entry<String, Client> entry : clients.entrySet()) {
									Client client = entry.getValue();
									client.send(matchStatusMessage);
								}
							}

							matchMutex.release();
							
						}
						
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
