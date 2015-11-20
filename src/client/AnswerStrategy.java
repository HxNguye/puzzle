package client;

import java.io.IOException;

public interface AnswerStrategy 
{
	public void sendToServer(String filePath) throws IOException;

}
