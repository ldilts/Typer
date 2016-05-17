package view;

import java.util.Scanner;

public class ClientGameView {
	
	private Scanner keyboardScanner = new Scanner(System.in);
	
	/*
	 * Helper Methods
	 */
	
	public String startNewMatch() {
		// Ask for a username
		System.out.print("Type a username: ");
		String username = keyboardScanner.nextLine();
		
		return username;
	}
	
	public void startMatch() {
		System.out.println("Start game!");
	}

}
