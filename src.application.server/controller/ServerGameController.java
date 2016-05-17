package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import factories.AbstractFactory;
import interfaces.Client;
import interfaces.Server;
import model.ServerMatch;
import model.MessageMaker;
import model.Ports;
import model.SocketType;
import producer.FactoryProducer;
import view.ServerGameView;

public class ServerGameController {

	private ServerMatch match;
	private ServerGameView view;

	private Server server;
	private HashMap<Integer, Client> clients;
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

		clients = new HashMap<Integer, Client>();

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
					
					int id = i + 1; 

					// Save client reference
					clients.put(id, client);

					// Add player to match
					match.addPlayer(id, username);
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

			// Start match Threads
			for (int i = 0; i < clients.size(); i++) {
				Client client = clients.get(i + 1);

				PlayerThread playerThread = new PlayerThread(match.getUsernameFromId(i + 1), client);
				playerThreads.add(playerThread);
				playerThread.start();
			}

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
	private class PlayerThread extends Thread {
		
		private String username;
		private Client client;
		
        public PlayerThread(String username, Client client) {
            this.username = username;
            this.client = client;
        }
		
		public void run() {
			// Send Start Match message
			String startMatchMessage = MessageMaker.getStartMatchMessage();
			try {
				client.send(startMatchMessage);
				
				while(true){
					try {
						String message = this.client.receive();
						System.out.println("		" + username + ": " + message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
