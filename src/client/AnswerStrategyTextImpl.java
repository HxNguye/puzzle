package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AnswerStrategyTextImpl implements AnswerStrategy
{
	PrintWriter output;
	BufferedReader input;
	
	@Override
	public void sendToServer(String message) throws IOException 
	{
		output.println(message);
		message = input.readLine();
		System.out.println("Client has received the following message: \n" + message);	
	}

}
