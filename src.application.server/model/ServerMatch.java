package model;

import java.util.ArrayList;

public class ServerMatch {
	
	public static final int MAX_PLAYERS = 2;
	
	private ArrayList<Player> players;
	private String[] words;
	
	public boolean isMatchReady = false;
	
	/*
	 * Public Constructors 
	 */
	
	public ServerMatch(String[] words) {
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
	
	public String getUsernameFromId(int id) {
		for (Player player : players) {
			if (player.getId() == id) {
				return player.getUsername();
			}
		}
		
		return "";
	}

	public StatusMessage addPlayer(int id, String username) {
		if (players.size() < MAX_PLAYERS) {
			Player newPlayer = new Player(id, username);
			players.add(newPlayer);
			
			isMatchReady = players.size() == MAX_PLAYERS;
			
			return StatusMessage.OK;
		} else {
			return StatusMessage.EXPECTATION_FAILED;
		}
	}
	
}
