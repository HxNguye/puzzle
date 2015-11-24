package client;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;

public class Client 
{	
	boolean answerResponse = false;
	private String message = "Standard default message";
	private Socket clientSocket;
	
	DataInputStream dataInFromServer = null;
	PrintWriter outToServer;
	BufferedReader bufferInFromServer;
	BufferedReader stdinput;
	
	TimerUtil timer = new TimerUtil();
	ScoringUtil score = new ScoringUtil();
	RankingEnum rank;

	//TODO: implement Timer + Scoring
	public Client(){}


	public void connect(InetAddress servername, int port) throws IOException
	{
		try
		{
			// try to connect to the server
			clientSocket = new Socket(servername, port);		

			// grab the output and input streams
			outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			bufferInFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// first initial score
			score.addScore(100);
		}
		catch (Exception e)
		{
			System.out.println("Can't accept client connection. ");
		}

		dataInFromServer = new DataInputStream(clientSocket.getInputStream());
		communicationHandler();
		tallyingScore();
	}

	public void PuzzleReceiver() throws Exception
	{
		String type = dataInFromServer.readUTF();
		if (type.equals("file"))
		{
			String filename = "Puzzle." + dataInFromServer.readUTF();
			int size = (int) dataInFromServer.readLong();
			FileOutputStream file = new FileOutputStream(filename);
			byte[] fileByte = new byte[size];
			BufferedOutputStream bos = new BufferedOutputStream(file);
			int bytesRead = dataInFromServer.read(fileByte,0,fileByte.length);
			int current = bytesRead;

			do 
			{
				bytesRead =	dataInFromServer.read(fileByte, current, (fileByte.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} 
			while(current < size);

			bos.write(fileByte, 0 , current);
			bos.flush();
			file.close();
			Desktop.getDesktop().open(new File(filename));
		}
		else
		{
			String puzzle = dataInFromServer.readUTF();
			Desktop.getDesktop().browse(new URI(puzzle));
		}
	}
	public void communicationHandler() throws IOException
	{
		stdinput= new BufferedReader( new InputStreamReader(System.in) );
		int state = dataInFromServer.readInt();
		int failcount = 0;
		while (state !=-1)
		{
			switch(state)
			{
			case 1:
				message = dataInFromServer.readUTF();
				System.out.println(message);
				outputHelper();
				state = dataInFromServer.readInt();
				break;

			case 2:
				try 
				{
					PuzzleReceiver();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outputHelper();
				state = dataInFromServer.readInt();
				break;
				
			case 3:
				Boolean answer = dataInFromServer.readBoolean();
				if (answer)
				{
					System.out.println(dataInFromServer.readUTF());
					score.addScore(100);
					state = dataInFromServer.readInt();
				}
				else
				{
					System.out.println(dataInFromServer.readUTF());
					failcount += 1;
					if (failcount %3==0)
					score.subScore(50);
					outputHelper();
					state = dataInFromServer.readInt();
				}
				break;
				
			case 4:
				message = dataInFromServer.readUTF();
				System.out.println(message);
				state = -1;
				break;
			}
		}
	}
	public void outputHelper() throws IOException
	{
		message = stdinput.readLine();
		if (message.contains("hint") || message.contains("clue"))
		{
			outToServer.println("hint");
			score.subScore(50);
		}
		else
		{
			outToServer.println(message);
		}
	}
	public void tallyingScore()
	{
		System.out.println("Your score is: " + score.getScore());
		score.upgradeRanking();
		rank = score.getRanking();
		System.out.println(rank.toString());
	}
}

