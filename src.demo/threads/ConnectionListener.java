package threads;

import java.io.IOException;

import interfaces.Client;
import interfaces.Server;

public class ConnectionListener implements Runnable{
	
	private Server server;
	
	public ConnectionListener(Server server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		
		while(true) {			
			try {
				Client toClient = this.server.hear();
			
				Thread threadIn = new Thread(new ReadThread(toClient));
				Thread threadOut = new Thread(new WriteThread(toClient));
				
				threadIn.start();
				threadOut.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
