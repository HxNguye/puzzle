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
			long time = System.currentTimeMillis();
			outputHelper(1, output, "\nEnter \"new\" if you are a new player or "
					+ "enter the answer to the last puzzle you completed:");

			// Implement some Puzzle handler here (new PuzzleHandler etc)
			// WIP
			textLoader(correct = new ArrayList<String>(),"correct.txt");
			textLoader(incorrect = new ArrayList<String>(),"incorrect.txt");
			while(true)
			{
				stage = getStage(input.readLine());
				if (stage == -1)
				{
					outputHelper(1,output,"Invalid");
				}
				else break;
			}
			puzzle = puzzles.get(stage);
			//send initial puzzle
			outputHelper(2,output, "");
			//handle the rest of the puzzles and then close the connection
			handlePuzzles(input, output);

			output.close();
			input.close();
			System.out.println("Request processed: " + time);
			clientSocket.close();
		} catch (IOException e) 
		{
			//report exception somewhere.
			e.printStackTrace();
		}
	}

	public int getStage(String in)
	{
		if(in.equals("new"))return 0;

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
					outputHelper(4,output,"You won! snarky snark");
					System.out.println("Player won");
					break;
				}
				//send a snarky message here
				System.out.println("Player was right");
				outputHelper(3,output, "T");
				puzzle = puzzles.get(stage);
				outputHelper(2,output, "");
			}
			
			else if(line.equals("hint"))
			{
				outputHelper(1,output, "Really? You really need a hint? Fine: \n\n" + puzzle.clue);
			}
			
			else
			{
				System.out.println("Player was wrong");
				outputHelper(3,output, "F");
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
	
	public void outputHelper(int state, DataOutputStream output, String message) throws IOException
	{
		Random rng = new Random();
		output.writeInt(state);
		switch (state)
		{
		case 1:
			if (message.equals("Invalid"))
			{
				output.writeUTF("Invalid choice: snarky snark comment");
			}
			else
			{
				output.writeUTF(message);
			}
			break;
		case 2:
			sendPuzzle(output);
			break;
		case 3:
			rng.setSeed(System.currentTimeMillis());
			if (message.equals("T"))
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
		case 4:
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
