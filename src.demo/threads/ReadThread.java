package threads;

import java.io.IOException;

import interfaces.Client;

public class ReadThread implements Runnable {

	Client inClient;

	public ReadThread(Client inClient) {
		this.inClient = inClient;
	}

	@Override
	public void run() {
		while(true){
			try {
				String message = this.inClient.receive();
				System.out.println("Mensagem: " + message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}