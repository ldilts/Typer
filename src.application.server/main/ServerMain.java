package main;

import controller.ServerGameController;
import model.ServerMatch;
import view.ServerGameView;

public class ServerMain {

	public static void main(String[] args) {
		ServerMatch model = new ServerMatch(new String[0]);
		ServerGameView view = new ServerGameView();
		new ServerGameController(model, view);
	}

}
