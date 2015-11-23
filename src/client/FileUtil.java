package client;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * FileUtil class used to receive puzzles for the client (player) side from the server
 */
@Deprecated
public class FileUtil 
{
	ScoringUtil score = new ScoringUtil();

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public FileUtil(byte[] byteFile, boolean response, int count, String message) throws FileNotFoundException
	{
		if (response = true)
		{
			File file = new File("");
			// convert array of bytes into a file
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			try
			{
				fileOutputStream.write(byteFile);
				fileOutputStream.close();

				// open the file
				Desktop.getDesktop().open(file);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}


			score.addScore(10);
			System.out.println(message);
		}
		else //response = false
		{
			score.subScore(5);
			System.out.println(message);
		}

		// TODO: Implement this into the client

	}



}
