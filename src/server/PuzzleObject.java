package server;

public class PuzzleObject 
{
	public String content;
	public String clue;
	public String type;
	public String answer;
	
	PuzzleObject()
	{
		
	}
	
	PuzzleObject(String file, String answer, String clue, String type)
	{
		content = file;
		this.clue = clue;
		this.type = type;
		this.answer = answer;
	}
	
	public String getcontent(String compare)
	{
		return content;
	}
	
	public String getClue(String compare)
	{
		return clue;
	}
	
	public String getType(String compare)
	{
		return type;
	}
	
	public Boolean getAnswer(String compare)
	{
		return answer.equals(compare);
	}
	
	public void setcontent(String file)
	{
		content = file;
	}
	
	public void setClue(String clue)
	{
		this.clue = clue;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}