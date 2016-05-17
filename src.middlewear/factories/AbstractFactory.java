package factories;

import interfaces.Client;
import interfaces.Server;
import model.SocketType;

public abstract class AbstractFactory {
	public abstract Client getClient(SocketType type, String serverAdress, int serverPort);
	public abstract Server getServer(SocketType type, int serverPort);
}
