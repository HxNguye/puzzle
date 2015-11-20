package client;
import javax.swing.JOptionPane;


public class ClientMain 
{


	public static void main(String[] args) throws Exception
	{
		Client c = new Client();
		
		if ( args.length != 1 )
		{
			System.out.println( "Usage: Client -s");
			System.out.println( "To answer with a file or text message to the server");
			return;
		}
		//TODO: implement other classes

		try
		{
			c.connect(9000);
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog(null, "Server is not started. Can not connect!");
		}
		
		
		


	}

}
