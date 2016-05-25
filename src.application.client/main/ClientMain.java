package main;

import controller.ClientGameController;
import model.ClientMatch;
import model.ServerMatch;
import view.ClientGameView;

public class ClientMain {
	
	public static void main(String[] args) {
		ClientMatch model = new ClientMatch(new String[0]);
		ClientGameView view = new ClientGameView();
		new ClientGameController(model, view);
	}

}
