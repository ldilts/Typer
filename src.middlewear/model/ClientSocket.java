package model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import interfaces.Client;

public class ClientSocket implements Client {

	private Socket client;
	private String serverAddress;
	private Integer serverPort;
	private Integer clientTimeout;
	
	public ClientSocket(String serverAdress, int serverPort) {
		this.serverAddress = serverAdress;
		this.serverPort = serverPort;
		this.clientTimeout = null;
	}
	
	public ClientSocket(String serverAdress, int serverPort, int timeout) {
		this.serverAddress = serverAdress;
		this.serverPort = serverPort;
		this.clientTimeout = timeout;
	}
	
	public ClientSocket(Socket clientSocket) {
		this.client = clientSocket;
	}
	
	@SuppressWarnings("resource")
	@Override
	public String receive() throws IOException {
		Scanner s = new Scanner(this.client.getInputStream());
		while(!s.hasNextLine()){
			
		}
		return s.nextLine();
	}

	@Override
	public void send(String message) throws IOException {
		PrintStream saida = new PrintStream(this.client.getOutputStream());
		saida.println(message);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finish() throws IOException {
		if(!this.client.isClosed()){
			this.client.close();
		}
	}

	@Override
	public boolean connect() throws IOException {
		try {
			
			if(clientTimeout != null) {
				SocketAddress socketAddress = new InetSocketAddress(this.serverAddress, this.serverPort);
				this.client.connect(socketAddress, this.clientTimeout);
			} else {
				this.client = new Socket(this.serverAddress, this.serverPort);
			}		
			
			Thread.sleep(25);
			
			return true;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
}
