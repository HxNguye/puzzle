package client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * CountdownTimer class for implementing timed puzzles
 */
public class TimerUtil
{
	int interval;
	Timer timer;
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	TimerUtil(){}
	
	/**
	 * Counddown timer method that decrements by 1 second
	 * @param String secs - Number of seconds to start the Countdown
	 */
	public void CountdownTimer(String secs) 
	{
		System.out.print("Input seconds => : ");
		int delay = 1000;
		int period = 1000;
		timer = new Timer();
		interval = Integer.parseInt(secs);
		System.out.println(interval);
		timer.scheduleAtFixedRate(new TimerTask() 
		{

			public void run() 
			{
				System.out.println(setInterval());

			}
		}, delay, period);
	}

	/**
	 * Helper method set intervals of 1 seconds
	 */
	private final int setInterval() 
	{
		if (interval == 1)
		{
			timer.cancel();
		}
		return --interval;
	}

}
