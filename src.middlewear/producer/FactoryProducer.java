package producer;

import factories.AbstractFactory;
import factories.ClientFactory;
import factories.ServerFactory;
import model.SocketType;

public class FactoryProducer {

	public static AbstractFactory getFactory(SocketType type) {
		switch (type) {
			case SERVER: return new ServerFactory();
			case CLIENT: return new ClientFactory();
			default: return null;
		}
	}

}
