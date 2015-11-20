package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AnswerStrategyFileImpl implements AnswerStrategy
{
	Socket clientSocket;
	
	@Override
	public void sendToServer(String filePath) throws IOException
	{
		File file = new File(filePath);

		byte[] bytes = new byte[16 * 1024];
		
		InputStream in = new FileInputStream(file);
		OutputStream out = clientSocket.getOutputStream();
		
		int count; 
		while ((count = in.read(bytes)) > 0)
		{
			out.write(bytes, 0 , count);
		}
		
		out.close();
		in.close();
	}

}
