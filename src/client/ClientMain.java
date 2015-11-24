package client;
import java.net.InetAddress;


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

		try
		{
			InetAddress server = InetAddress.getByName(args[0]);
			c.connect(server,Integer.parseInt(args[1]));
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
