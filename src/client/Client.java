package client;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client 
{	
	int port_number;
	boolean answerResponse = false;
	String message = "Standard default message";
	String localhost = "127.0.0.1";
	Socket clientSocket;
	InputStream in = null;
//	OutputStream out = null;
	
	PrintWriter outToServer;
	BufferedReader inFromServer;

	TimerUtil timer = new TimerUtil();
	ScoringUtil score = new ScoringUtil();
	RankingEnum rank;


	public Client(){}


	public void connect(int port) throws IOException
	{
		try
		{
			port_number = port;		
			// try to connect to the server
			clientSocket = new Socket(localhost, port_number);		

			// grab the output and input streams
			outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// first initial score
			score.addScore(100);
		}
		catch (Exception e)
		{
			System.out.println("Can't accept client connection. ");
		}

		try
		{
			in = clientSocket.getInputStream();
		}
		catch (Exception e)
		{
			System.out.println("Can't get socket input stream. ");
		}

		byte[] bytes = new byte[1024];

		int count;
		while ((count = in.read(bytes)) > 0)
		{
			if (answerResponse = true)
			{
				File file = new File("");
				// convert array of bytes into a file
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				try
				{
					fileOutputStream.write(bytes);
					fileOutputStream.close();

					// open the file
					Desktop.getDesktop().open(file);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}


				score.addScore(10);
				System.out.println(message);
			}
			else //response = false
			{
				score.subScore(5);
				System.out.println(message);
			}
		}


//		out.close();
		in.close();
	}

	public void communicate(String message) throws IOException
	{
		try
		{
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			//			String fromServer;
			String fromClient = message;
			fromClient = stdIn.readLine();
			while(fromClient != null)
			{
				outToServer.println(fromClient);
			}	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public void FileHelper(byte[] byteFile, boolean response, String message) throws FileNotFoundException
	{
		// if correct answer
		if (response = true)
		{
			File file = new File("");
			// convert array of bytes into a file
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			try
			{
				fileOutputStream.write(byteFile);
				fileOutputStream.close();

				// open the file
				Desktop.getDesktop().open(file);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			score.addScore(1000);
			System.out.println(message);
		}
	}
}

