package client;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CountdownTimer class for implementing timed puzzles
 */
public class TimerUtil
{
	static int interval;
	static Timer timer;
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	TimerUtil()
	{
		
	}
	
	/**
	 * Counddown timer method that decrements by 1 second
	 */
	public static void CountdownTimer() 
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Input seconds => : ");
		String secs = sc.nextLine();
		int delay = 1000;
		int period = 1000;
		timer = new Timer();
		interval = Integer.parseInt(secs);
		System.out.println(secs);
		timer.scheduleAtFixedRate(new TimerTask() 
		{

			public void run() 
			{
				System.out.println(setInterval());

			}
		}, delay, period);
		sc.close();
	}

	/**
	 * @deprecated
	 * Helper method for testing
	 */
	private static final int setInterval() 
	{
		if (interval == 1)
		{
			timer.cancel();
		}
		return --interval;
	}

	/**
	 * @deprecated
	 * Main function to test
	 */
	public static void main(String[] args) 
	{
		TimerUtil t = new TimerUtil();
		t.CountdownTimer();
	}
}
