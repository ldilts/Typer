package interfaces;

import java.io.IOException;

public interface Server {
	Client hear() throws IOException;
	void finish() throws IOException;
}
