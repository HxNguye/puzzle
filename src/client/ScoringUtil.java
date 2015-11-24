package client;

public class ScoringUtil 
{
	int playerScore;
	RankingEnum playerRank;
	
	/**
	 * CONSTRUCTIOR
	 */
	public ScoringUtil()
	{
		setScore(0);
		setRanking(RankingEnum.F);
	}

	/**
	 * Method to increment the player score
	 * @param int currentPlayerScore
	 */
	public void addScore(int currentPlayerScore)
	{
		playerScore += currentPlayerScore;
	}

	/**
	 * Method to decrement the player score
	 * @param int currentPlayerScore
	 */
	public void subScore(int currentPlayerScore)
	{
		playerScore -= currentPlayerScore;
	}

	/**
	 * Method to set the Player Score
	 * ]@param int currentPlayerScore
	 */
	private void setScore(int currentPlayerScore)
	{
		playerScore = currentPlayerScore;
	}

	/**
	 * Method to get the Player Score
	 */
	public int getScore()
	{
		return playerScore;
	}

	/**
	 * Method to Upgrade the rank based on the amount of the player score
	 */
	public RankingEnum upgradeRanking()
	{
		if (playerScore >= 500)
		{
			setRanking(RankingEnum.S);
		}	
		else if(playerScore >= 400)
		{
			setRanking(RankingEnum.A);
		}
		else if(playerScore >= 300)
		{
			setRanking(RankingEnum.B);
		}
		else if(playerScore >= 200)
		{
			setRanking(RankingEnum.C);
		}
		else if(playerScore >= 100)
		{
			setRanking(RankingEnum.D);
		}
		else // <100
		{
			setRanking(RankingEnum.F);
		}
		return playerRank;
	}

	/**
	 * Method to set the Player's Rank
	 * @param Enum newRank
	 */
	private void setRanking(RankingEnum newRank)
	{
		playerRank = newRank;
		
	}

	/**
	 * Method to get the Player's Rank
	 */
	public RankingEnum getRanking()
	{
		return playerRank;
	}
}
