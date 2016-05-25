package model;

public class CurrentPlayer {
	private static CurrentPlayer instance = null;
	private String id;
	private String username;
	private int level;
	
	protected CurrentPlayer() {
		// Exists only to defeat instantiation.
		this.level = 0;
	}
	
	public static CurrentPlayer getInstance() {
		if(instance == null) {
			instance = new CurrentPlayer();
		}
		
		return instance;
	}
	
	/*
	 * Getters an Setters
	 */
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
