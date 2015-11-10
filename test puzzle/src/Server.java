
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server implements Runnable
{

	protected int          serverPort   = 8080;
	protected ServerSocket serverSocket = null;
	protected boolean      isStopped    = false;
	protected Thread       runningThread= null;
	private int Threadcount = 0;

	public Server(int port)
	{
		this.serverPort = port;
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
			new Thread(new ConnectionHandler(clientSocket, "Multithreaded Server", ++Threadcount)).start();
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

	public static void main(String[] args) throws Exception
	{
		Server server = new Server(9000);
		new Thread(server).start();
		BufferedReader input= new BufferedReader( new InputStreamReader(System.in) );
		String temp = input.readLine();
		//		try 
		//		{
		//    	    Thread.sleep(20 * 1000);
		//    	} 
		//		catch (InterruptedException e) 
		//		{
		//    	    e.printStackTrace();
		//    	}
		while (!temp.equals("stop"))
		{
			temp = input.readLine();
		}
		System.out.println("Stopping Server");
		server.stop();

	}
}