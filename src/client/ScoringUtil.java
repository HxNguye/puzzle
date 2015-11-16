package client;

public class ScoringUtil 
{
	int playerScore;
	RankingEnum playerRank;
	
	public ScoringUtil()
	{
		setScore(0);
		setRanking(RankingEnum.F);
	}

	public void addScore(int newPlayerScore)
	{
		playerScore += newPlayerScore;
	}
	
	public void subScore(int newPlayerScore)
	{
		playerScore -= newPlayerScore;
	}
	
	private void setScore(int newPlayerScore)
	{
		playerScore = newPlayerScore;
	}

	public int getSocre()
	{
		return playerScore;
	}
	
	public RankingEnum upgradeRanking()
	{
		if (playerScore >= 50000)
		{
			setRanking(RankingEnum.S);
		}	
		else if(playerScore >= 40000)
		{
			setRanking(RankingEnum.A);
		}
		else if(playerScore >= 30000)
		{
			setRanking(RankingEnum.B);
		}
		else if(playerScore >= 20000)
		{
			setRanking(RankingEnum.C);
		}
		else if(playerScore >= 10000)
		{
			setRanking(RankingEnum.D);
		}
		else //(playerScore > 10000)
		{
			setRanking(RankingEnum.F);
		}
		return playerRank;
	}
	
	private void setRanking(RankingEnum newRank)
	{
		playerRank = newRank;
		
	}
	public RankingEnum getRanking()
	{
		return playerRank;
	}
}
