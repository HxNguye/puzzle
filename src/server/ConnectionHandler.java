package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**

 */
public class ConnectionHandler implements Runnable
{

	protected Socket clientSocket = null;
	private int count = 0;
	private ArrayList<String> correct, incorrect;
	protected ArrayList<PuzzleObject> puzzles;
	protected int stage;
	protected PuzzleObject puzzle; 

	public ConnectionHandler(Socket clientSocket, int count, ArrayList<PuzzleObject> puzzles) 
	{
		this.clientSocket = clientSocket;
		this.count = count;
		this.puzzles = puzzles;
	}

	public void run() 
	{
		try 
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream output = new DataOutputStream( clientSocket.getOutputStream() );
			outputHelper(1, output, "\nWelcome to Distributed System Puzzle. \n"
					+ "The part of the class where you have to be in attendance. \n\n"
					+ "If this is your first time, please type 'I'm a newbie'. \n"
					+ "If this is NOT your first time, you know what to do.\n\n"
					+ "You will be given either a picture or a website. That is your clue.\n"
					+ "Type in your answer, if you are stuck, try typing 'hint' or 'clue'.\n"
					+ "You are scored based on your performance, so pay attention.", false);

			// Implement some Puzzle handler here (new PuzzleHandler etc)
			// WIP
			System.out.println("Player #" + count + " connected.");
			textLoader(correct = new ArrayList<String>(),"correct.txt");
			textLoader(incorrect = new ArrayList<String>(),"incorrect.txt");
			while(true)
			{
				stage = getStage(input.readLine());
				if (stage == -1)
				{
					outputHelper(1,output,"Invalid", false);
				}
				else break;
			}
			puzzle = puzzles.get(stage);
			//send initial puzzle
			outputHelper(2,output, "", false);
			//handle the rest of the puzzles and then close the connection
			handlePuzzles(input, output);

			output.close();
			input.close();
			clientSocket.close();
		} catch (IOException e) 
		{
			System.out.println("Something went wrong, you wrote this. Go check.");
			e.printStackTrace();
		}
	}

	public int getStage(String in)
	{
		if(in.contains("newbie"))return 0;

		//checks for the puzzle with the matching answer
		else 
		{
			for(int i = 0; i < puzzles.size(); i++)
			{
				//if the puzzle exists send return the number of the next stage
				if(in.equals(puzzles.get(i).answer)) return i+1;
			}
		}
		return -1;
	}

	public void handlePuzzles(BufferedReader input, DataOutputStream output) throws IOException
	{
		//read and process inputs until the client sends exit
		String line = input.readLine();
		while (!line.equals("exit"))
		{
			//if the client send a correct answer
			if (puzzle.getAnswer(line))
			{
				if (++stage == puzzles.size())
				{
					outputHelper(4,output,"Oh you actually won, here's an imaginary cake. Let's see how good you did.", true);
					System.out.println("Player " + count +  " won");
					break;
				}
				System.out.println("Player " + count +  " was right");
				outputHelper(3,output,"", true);
				puzzle = puzzles.get(stage);
				outputHelper(2,output, "", false);
			}
			
			else if(line.equals("hint"))
			{
				outputHelper(1,output, "\n" + puzzle.clue, false);
			}
			
			else
			{
				System.out.println("Player " + count +  " was wrong");
				outputHelper(3,output, "", false);
			}
			line = input.readLine();
		}
	}

	public void sendPuzzle(DataOutputStream output) throws IOException
	{
		if(puzzle.type.equals("URL"))
		{
			output.writeUTF(puzzle.type);
			output.writeUTF(puzzle.content);
		}
		else
		{
			output.writeUTF(puzzle.type);
			output.writeUTF(puzzle.content.substring(puzzle.content.length()-3));
			File fil = new File(puzzle.content);
			output.writeLong(fil.length());
			FileInputStream file = new FileInputStream(puzzle.content);
			byte[] fileByte = new byte[1500];
			int numread;
			while((numread = file.read(fileByte)) != -1)
			{
				output.write(fileByte,0,numread);
			}
			file.close();
		}
	}
	
	public void outputHelper(int state, DataOutputStream output, String message, boolean check) throws IOException
	{ //Helps with sending message and puzzles.
		Random rng = new Random();
		output.writeInt(state);
		message = "\n" + message;
		switch (state)
		{
		case 1: //Sends a message
			if (message.equals("Invalid"))
			{
				output.writeUTF("\nSurely you typed something wrong, try again.\n"
						+ " It helps to, you know, look at the instructions?");
			}
			else
			{
				output.writeUTF(message);
			}
			break;
		case 2: //Sends a puzzle
			sendPuzzle(output);
			break;
		case 3: //Sends an answer response
			rng.setSeed(System.currentTimeMillis());
			System.out.println(message);
			if (check)
			{
				output.writeBoolean(true);
				output.writeUTF(correct.get(rng.nextInt(correct.size()-1)));
			}
			else
			{
				output.writeBoolean(false);
				output.writeUTF(incorrect.get(rng.nextInt(incorrect.size()-1)));
			}
			break;
		case 4: //End game
			output.writeUTF(message);
			break;
		}
	}
	
	public void textLoader(ArrayList<String> list, String file) throws IOException
	{
			
			BufferedReader inFile= new BufferedReader(new FileReader(file));
			String line = inFile.readLine();
			while(line != null){
				list.add(line);
				line  = inFile.readLine();
			}
			inFile.close();
	}
}
