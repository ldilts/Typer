package model;

import java.util.ArrayList;
import java.util.HashMap;

import interfaces.Client;

public class ServerMatch {
	
	public static final int MAX_PLAYERS = 2;
	
	private HashMap<String, Player> players;
	private String[] words;
	private String statusString = "";
	
	public boolean isMatchReady = false;
	private String winner = null;
	
	/*
	 * Public Constructors 
	 */
	
	public ServerMatch(String[] words) {
		super();
		this.words = words;
		this.players = new HashMap<String, Player>();
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
	
	public String getStatus() {
		return statusString;
	}
	
//	public void setStatus(String status) {
//		this.words = words;
//	}
	
	public String getWinner() {
		return winner;
	}

//	public void setWinner(String winner) {
//		this.winner = winner;
//	}
	
	/*
	 * Helper Methods
	 */

	public String getUsernameFromId(String id) {
		return players.get(id).getUsername();
	}

	public String addPlayer(String username) {
		if (players.size() < MAX_PLAYERS) {
			Player newPlayer = new Player(username);
			String id = newPlayer.getId();
			players.put(id, newPlayer);
			
			statusString += id + "=" + username + "=" + 0 + "*";
			isMatchReady = players.size() == MAX_PLAYERS;
			
			return id;
		} else {
			return "";
		}
	}
	
	public void updatePlayerLevel(String id, int level) {
		statusString = "";
		
		Player playerToUpdate = players.get(id);
		playerToUpdate.setLevel(level);
		players.put(id, playerToUpdate);
		
		if (playerToUpdate.getLevel() == words.length) {
			winner = playerToUpdate.getId();
		}
		
		for (HashMap.Entry<String, Player> entry : players.entrySet()) {
			Player player = entry.getValue();
			
			statusString += player.getId() + "=" + player.getUsername() + "=" + player.getLevel() + "*";
		}
	}
	
}
