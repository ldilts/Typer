package model;

import java.util.ArrayList;

public class ClientMatch {
	
	private ArrayList<Player> players;
	private String[] words;
	
	/*
	 * Public Constructors 
	 */
	
	public ClientMatch(String[] words) {
		super();
		this.words = words;
		this.players = new ArrayList<Player>();
	}
	
	/*
	 * Getters and Setters
	 */
	
	public String[] getWords() {
		return words;
	}
	
	public void setWords(String[] words) {
		this.words = words;
	}
	
	/*
	 * Helper Methods
	 */

	public void addPlayer(String username) {
		int id = players.size();
		Player newPlayer = new Player(id, username);
		players.add(newPlayer);
	}

}
