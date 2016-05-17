package factories;

import interfaces.Client;
import interfaces.Server;
import model.ServerSocket;
import model.SocketType;
import model.ServerDatagramSocket;

public class ServerFactory extends AbstractFactory {

	@Override
	public Client getClient(SocketType type, String invalid1, int invalid2) {
		return null;
	}

	@Override
	public Server getServer(SocketType type, int port) {
		if (type == null) {
			return null;
		}

		switch (type) {
			case SERVER_UDP: return new ServerDatagramSocket(port);
			case SERVER_TCP: return new ServerSocket(port);
			default: return null;
		}
	}

}
