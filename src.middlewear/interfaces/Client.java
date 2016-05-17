package interfaces;

import java.io.IOException;

public interface Client {
	String receive() throws IOException;
	void send(String message) throws IOException;
	boolean connect() throws IOException;
	void finish() throws IOException;
}
