package client;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;

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

//TODO: implement Timer + Scoring
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
		
		String message;
		DataInputStream uti = new DataInputStream(in);
		for (int i = 0; i <3; i++)
		{
			message = uti.readUTF();
			System.out.println(message);
		}
		BufferedReader stdinput= new BufferedReader( new InputStreamReader(System.in) );
		message = stdinput.readLine();
		while (true)
		{
		
		
		outToServer.println(message);
		message = uti.readUTF();
		try {
			ContentHelper(message, stdinput, uti);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message = stdinput.readLine();
		outToServer.println(message);
		}
//		in.close();
	}

	public void communicate(String message) throws IOException
	{
		try
		{
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
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
	
	public void ContentHelper(String type, BufferedReader inserver, DataInputStream uti) throws Exception
	{
			if (type.equals("file"))
			{
				String filename = "Puzzle." + uti.readUTF();
				int size = (int) uti.readLong();
				FileOutputStream file = new FileOutputStream(filename);
				byte[] fileByte = new byte[size];
				BufferedOutputStream bos = new BufferedOutputStream(file);
				int bytesRead = uti.read(fileByte,0,fileByte.length);
				int current = bytesRead;

				do {
					bytesRead =
							uti.read(fileByte, current, (fileByte.length-current));
					if(bytesRead >= 0) current += bytesRead;
				} while(current < size);

				bos.write(fileByte, 0 , current);
				bos.flush();
				file.close();
				Desktop.getDesktop().open(new File(filename));
			}
			else
			{
				String puzzle = uti.readUTF();
				Desktop.getDesktop().browse(new URI(puzzle));
			}
			score.addScore(1000);
//			System.out.println(message);
	}
}

