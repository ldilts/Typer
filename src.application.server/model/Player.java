package model;

public class Player {
	
	private int id;
	private int level;
	private String username;
	
	/*
	 * Public Constructor
	 */
	
	public Player(int id, String username) {
		super();
		this.id = id;
		this.level = 0;
		this.username = username;
	}
	
	/*
	 * Getters and Setters
	 */
	
	public int getId() {
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
