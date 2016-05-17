package threads;

import java.io.IOException;
import java.util.Scanner;

import interfaces.Client;

public class WriteThread implements Runnable {

	Client outClient;

	public WriteThread(Client outClient) {
		this.outClient = outClient;
	}

	@Override
	public void run() {
		Scanner teclado = new Scanner(System.in);
		while(true) {
			try {
				
				String console = teclado.nextLine();
				this.outClient.send(console);
				
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
		teclado.close();
	}
}