package model;

import interfaces.Client;

public class WaitlistPlayer {
	
	private Client client;
	private String username;
	
	/*
	 * Public Constructors
	 */
	
	public WaitlistPlayer(Client client, String username) {
		super();
		this.client = client;
		this.username = username;
	}

	/*
	 * Getters and Setters
	 */
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
