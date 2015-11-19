package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class Client 
{	
	int port_number;
	InetAddress loopback;
	InetAddress localhost;
	Socket client_socket;
	PrintWriter output;
	BufferedReader input;
	
	public Client(int port) throws IOException
	{
		port_number = port;
		loopback = InetAddress.getLoopbackAddress();
		
		// try to connect to the server
		client_socket = new Socket(loopback, port_number);		
		
		// grab the output and input streams
		output = new PrintWriter(client_socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
	}
	
	public void communicate(String message) throws IOException
	{
		output.println(message);
		message = input.readLine();
		System.out.println("Client has received the following message: \n" + message);
	}
	
	
	
	

	
}

