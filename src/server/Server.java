package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server implements Runnable
{

	protected int          serverPort   = 8080;
	protected ServerSocket serverSocket = null;
	protected boolean      isStopped    = false;
	protected Thread       runningThread= null;
	private int Threadcount = 0;
	public static ArrayList<PuzzleObject> puzzle;

	public Server(int port)
	{
		this.serverPort = port;
		puzzle = new ArrayList<PuzzleObject>();
		try {
			loadPuzzle();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run()
	{
		synchronized(this)
		{
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while(! isStopped())
		{
			Socket clientSocket = null;
			try 
			{
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) 
			{
				if(isStopped()) 
				{
					System.out.println("Server Stopped.") ;
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			new Thread(new ConnectionHandler(clientSocket, ++Threadcount, puzzle)).start();
		}
		System.out.println("Server Stopped.") ;
	}


	private synchronized boolean isStopped() 
	{
		return this.isStopped;
	}

	public synchronized void stop()
	{
		this.isStopped = true;
		try 
		{
			this.serverSocket.close();
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() 
	{
		try 
		{
			this.serverSocket = new ServerSocket(this.serverPort);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}
	
	private void loadPuzzle() throws IOException{
		BufferedReader inFile= new BufferedReader(new FileReader("puzzle.txt"));
		String line = inFile.readLine();
		while(line != null){
			String[] args = line.split(",");
			puzzle.add(new PuzzleObject(args[0], args[1], args[2], args[3]));
			line  = inFile.readLine();
		}
		inFile.close();
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length == 1)
		{
		Server server = new Server(Integer.parseInt(args[0]));
		new Thread(server).start();
		BufferedReader input= new BufferedReader( new InputStreamReader(System.in) );
		String temp = input.readLine();
		while (!temp.equals("stop"))
		{
			temp = input.readLine();
		}
		System.out.println("Stopping Server");
		server.stop();
		}
		else
		{
			System.out.println("Usage: Server <port>");
		}

	}
}