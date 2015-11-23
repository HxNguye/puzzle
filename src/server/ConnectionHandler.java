package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**

 */
public class ConnectionHandler implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	private int count = 0;
	protected ArrayList<PuzzleObject> puzzles;
	protected int stage;
	protected PuzzleObject puzzle; 

	public ConnectionHandler(Socket clientSocket, String serverText, int count, ArrayList<PuzzleObject> puzzles) {
		this.clientSocket = clientSocket;
		this.serverText   = serverText;
		this.count = count;
		this.puzzles = puzzles;
	}

	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream output = new DataOutputStream( clientSocket.getOutputStream() );            long time = System.currentTimeMillis();
			//String temp = input.readLine();
			output.writeUTF("\nThread for player #" + this.count + " responded");
			output.writeUTF("\nPlayer ID: " + UUIDGenerator.generateUUID()); // Test
			output.writeUTF("\nEnter \"new\" if you are a new player or "
					+ "enter the answer to the last puzzle you completed:");

			// Implement some Puzzle handler here (new PuzzleHandler etc)
			// WIP
			while(true){
				stage = getStage(input.readLine());
				if (stage == -1){
					output.writeUTF("That was not a valid entry"
							+ "\nEnter \"new\" if you are a new player or "
							+ "enter the answer to the last puzzle you completed:");

				}
				else break;
			}
			puzzle = puzzles.get(stage);
			//send initial puzzle
			sendPuzzle(output);
			//handle the rest of the puzzles and then close the connection
			handlePuzzles(input, output);

			output.close();
			input.close();
			System.out.println("Request processed: " + time);
			clientSocket.close();
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
	}

	public int getStage(String in){
		if(in.equals("new"))return 0;
		
		//checks for the puzzle with the matching answer
		else {
			for(int i = 0; i < puzzles.size(); i++){
				//if the puzzle exists send return the number of the next stage
				if(in.equals(puzzles.get(i).answer)) return i+1;
			}
		}
		return -1;
	}

	public void handlePuzzles(BufferedReader input, DataOutputStream output) throws IOException{
		//read and process inputs until the client sends exit
		String line = input.readLine();
		while (!line.equals("exit")){
			//if the client send a correct answer
			//TODO: sort out answer handler
			if (puzzle.getAnswer(line)){
				if (++stage == puzzles.size()){
					output.writeUTF("You Win!");
					System.out.println("Player won");
					break;
				}
				//send a snarky message here
				System.out.println("Player was right");
				puzzle = puzzles.get(stage);
				sendPuzzle(output);
			}
			else if(line.equals("clue")){
				output.writeUTF(puzzle.clue);
			}
			else{
				System.out.println("Player was wrong");
			}
		}
	}

	public void sendPuzzle(DataOutputStream output) throws IOException{
		if(puzzle.type.equals("URL")){
			output.writeUTF(puzzle.type);
			output.writeUTF(puzzle.content);
		}
		else{
			output.writeUTF(puzzle.type);
			output.writeUTF(puzzle.content.substring(puzzle.content.length()-3));
			File fil = new File(puzzle.content);
			output.writeLong(fil.length());
			FileInputStream file = new FileInputStream(puzzle.content);
			byte[] fileByte = new byte[1500];
			int numread;
			while((numread = file.read(fileByte)) != -1){
				output.write(fileByte,0,numread);
			}
			file.close();
		}
	}
}
