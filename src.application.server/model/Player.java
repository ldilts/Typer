package model;

import java.util.UUID;

public class Player {
	
	private String id;
	private int level;
	private String username;
	
	/*
	 * Public Constructor
	 */
	
	public Player(String username) {
		super();
		this.id = UUID.randomUUID().toString();
		this.level = 0;
		this.username = username;
	}
	
	public Player(String id, String username) {
		super();
		this.id = id;
		this.level = 0;
		this.username = username;
	}
	
	public Player(String id, String username, int level) {
		super();
		this.id = id;
		this.level = level;
		this.username = username;
	}
	
	/*
	 * Getters and Setters
	 */
	
	public String getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
