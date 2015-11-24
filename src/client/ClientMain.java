package client;
import java.net.InetAddress;

import javax.swing.JOptionPane;


public class ClientMain 
{


	public static void main(String[] args) throws Exception
	{
		Client c = new Client();
		
		if ( args.length != 2 )
		{
			System.out.println( "Usage: Client <servername> <port>");
			return;
		}
		//TODO: implement other classes

		try
		{
			InetAddress server = InetAddress.getByName(args[0]);
			c.connect(server,Integer.parseInt(args[1]));
		}
		catch( Exception e )
		{
			//JOptionPane.showMessageDialog(null, "Server is not started. Can not connect!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		


	}

}
