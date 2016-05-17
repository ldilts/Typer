package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import interfaces.Client;
import interfaces.Server;

public class ServerDatagramSocket implements Server {

    private InetAddress clientAddress;
    private int clientPort;
    private int serverPort;
    private DatagramSocket datagramSocket;
    
    private int currentPort;

    public ServerDatagramSocket(int serverPort)  {
        this.serverPort = serverPort;
        this.currentPort = 50000;
        try {
            this.datagramSocket = new DatagramSocket(this.serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Server Online");
    }
    
    private void send(String message) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), this.clientAddress, this.clientPort);
        datagramSocket.send(datagramPacket);
    }

    @Override
    public void finish() throws IOException {
        if (!datagramSocket.isClosed()) {
            this.datagramSocket.close();
        }
    }

	@Override
	public Client hear() throws IOException {
		
		byte[] msg = new byte[512];
		DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length);
	    
	    this.datagramSocket.receive(datagramPacket);
	    
		this.clientPort = datagramPacket.getPort();
	    this.clientAddress = datagramPacket.getAddress();
	    
	    String data = new String(datagramPacket.getData());
	    String request = data.substring(0, datagramPacket.getLength());
	    
		if (request.equals("#request_connection")) {
			this.send("#connect_accepted," + this.currentPort);
		}
		
		DatagramSocket ds = new DatagramSocket(this.currentPort);
		
		this.currentPort++;
		
		Client toClient = new ClientDatagramSocket(this.clientPort, this.clientAddress.getHostAddress(), ds);
		return toClient;
	}
	
	public int getPort(){
		return this.serverPort;
	}

}
