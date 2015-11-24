package test;

import static org.junit.Assert.*;
import org.junit.Test;
import client.*;

public class ScoringUtilTest {
	ScoringUtil score;
	RankingEnum rank; 
	
	public ScoringUtilTest() {
		score = new ScoringUtil();
	}
	
	@Test
	public void getScoreTest() {
		score.addScore(12341234);
		score.subScore(23452);
		assertEquals(12317782,score.getScore(),0);
	}
	
	@Test
	public void rankingTest() {
		assertEquals("Impressive", score.getRanking());
	}
	
	public void upgradeRankingTest() {
		rank = score.upgradeRanking();
		// Reset score to 0
		score.subScore(score.getScore());
		assertEquals(0,score.getScore());
		// Set score to 50 then check rank
		score.addScore(50);
		assertEquals("Impressive", rank);
		// Set score to 49
		score.subScore(1);
		assertEquals("You're pretty good!", rank);
		// Set score to 40
		score.subScore(9);
		assertEquals("You're pretty good!", rank);
		// Set score to 39
		score.subScore(1);
		assertEquals("Almost gets you almost", rank);
		// Set score to 30
		score.subScore(9);
		assertEquals("Almost gets you almost", rank);
		// Set score to 29
		score.subScore(1);
		assertEquals("You are Meh!", rank);
		// Set score to 20
		score.subScore(9);
		assertEquals("You are Meh!", rank);
		// Set score to 19
		score.subScore(1);
		assertEquals("Total FAIL!", rank);
		// Set score to 10
		score.subScore(9);
		assertEquals("Total FAIL!", rank);
		// Set score to 9
		score.subScore(1);
		assertEquals("You are terrible!", rank);
	}
}