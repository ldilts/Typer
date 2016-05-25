package model;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMatch {
	
	private HashMap<String, Player> players;
	private String[] words;
	private static final String delims = "[=*]+";
	
	private String winner = null;
	
	/*
	 * Public Constructors 
	 */
	
	public ClientMatch(String[] words) {
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
	
	public String getWinner() {
		return winner;
	}

//	public void setWinner(String winner) {
//		this.winner = winner;
//	}
	
	/*
	 * Helper Methods
	 */

	public void addPlayer(String id, String username) {
		Player newPlayer = new Player(id, username);
		players.put(id, newPlayer);
	}
	
	public void updateMatch(String status) {
		String[] tokens = status.split(delims);
		
		for (int i = 0; i < (tokens.length / 3); i++) {
			String id = tokens[i*3];
			String username = tokens[(i*3)+1];
			int level = Integer.parseInt(tokens[(i*3)+2]);
			
			Player player = players.get(id);
			
			if (player != null) {
				player.setUsername(username);
				player.setLevel(level);
			} else {
				player = new Player(id, username, level);
			}
			
			if (player.getLevel() == words.length) {
				winner = player.getUsername();
			}
			
			players.put(id, player);
			
			System.out.println("Added player: " + username);
		}
	}

}
