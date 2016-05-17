package model;

import java.io.IOException;
import java.net.*;

import interfaces.Client;

public class ClientDatagramSocket implements Client {

	private Integer serverPort;
	private InetAddress serverAddress;
	private Integer clientPort;
	private InetAddress clientAddress;
	private DatagramSocket datagramSocket;
	
	private Integer newPort;
	
	private boolean sideServer;

	public ClientDatagramSocket(int serverPort, String serverAddress) {
		try {
			this.serverPort = serverPort;
			this.serverAddress = InetAddress.getByName(serverAddress);
			this.clientPort = null;
			this.clientAddress = null;
			this.datagramSocket = new DatagramSocket();
			this.sideServer = false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public ClientDatagramSocket(int clientPort, String clientAddress, DatagramSocket ds) {
		try {
			this.clientPort = clientPort;
			this.clientAddress = InetAddress.getByName(clientAddress);
			this.datagramSocket = ds;
			this.sideServer = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String receive() throws IOException {
		
		byte[] msg = new byte[512];
		DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length);
		
		this.datagramSocket.receive(datagramPacket);
		
		String data = new String(datagramPacket.getData());
		String message = data.substring(0, datagramPacket.getLength());
		
		return message;
	}

	@Override
	public void send(String message) throws IOException {
		
		if(this.sideServer == true){
			DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), this.clientAddress, this.clientPort);
			this.datagramSocket.send(datagramPacket);
		} else {
			if(this.newPort != null){
				DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), this.serverAddress, this.newPort);
				this.datagramSocket.send(datagramPacket);
			} else {
				DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), this.serverAddress, this.serverPort);
				this.datagramSocket.send(datagramPacket);
			}
			
		}
	}

	@Override
	public void finish() throws IOException {
		if (!datagramSocket.isClosed()) {
			this.datagramSocket.close();
		}
	}

	@Override
	public boolean connect() throws IOException {
		
		this.send("#request_connection");
		
		String response = this.receive();
		
		String reponses[] = response.split(",");
		
		String conn = reponses[0];
		
		if (conn.equals("#connect_accepted")) {
			
			this.newPort = Integer.parseInt(reponses[1]);
			return true;
		} else {
			return false;
		}
	}
}
