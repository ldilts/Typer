package factories;

import interfaces.Client;
import interfaces.Server;
import model.ClientSocket;
import model.SocketType;
import model.ClientDatagramSocket;

public class ClientFactory extends AbstractFactory {

	@Override
	public Client getClient(SocketType type, String serverAddress, int serverPort) {
		if (type == null) {
			return null;
		}

		switch (type) {
			case CLIENT_UDP: return new ClientDatagramSocket(serverPort,serverAddress);
			case CLIENT_TCP: return new ClientSocket(serverAddress, serverPort);
			default: return null;
		}
	}

	@Override
	public Server getServer(SocketType type, int invalid) {
		return null;
	}

}
