package main;

import controller.ClientGameController;
import model.ServerMatch;
import view.ClientGameView;

public class ClientMain {
	
	public static void main(String[] args) {
		ServerMatch model = new ServerMatch(new String[0]);
		ClientGameView view = new ClientGameView();
		new ClientGameController(model, view);
	}

}
