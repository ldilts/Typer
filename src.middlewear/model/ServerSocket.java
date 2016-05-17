package model;

import java.io.IOException;
import java.net.Socket;

import interfaces.Client;
import interfaces.Server;

public class ServerSocket implements Server {
	
	private java.net.ServerSocket server;
	private Socket client;
	private int port;
	
	public ServerSocket(int port) {
		try {
			this.port = port;
			this.server = new java.net.ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Server opened on port " + this.port);
	}
	
	@Override
	public Client hear() throws IOException {
		 this.client = server.accept();
		 return new ClientSocket(this.client);
	}

	@Override
	public void finish() throws IOException {
		if(!server.isClosed()) {
			this.server.close();
		}		
	}
	
	public int getPort(){
		return this.port;
	}
	
	public String getAddress(){
		return server.getInetAddress().getHostAddress();
	}
}
