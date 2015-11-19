package server;

public class PuzzleObject {
	public String content;
	public String clue;
	public String type;
	public String answer;
	
	PuzzleObject(){
		
	}
	
	PuzzleObject(String file, String answer, String clue, String type){
		content = file;
		this.clue = clue;
		this.type = type;
		this.answer = answer;
	}
	
	public Boolean getcontent(String compare){
		return content.equals(compare);
	}
	public Boolean getClue(String compare){
		return clue.equals(compare);
	}
	public Boolean getType(String compare){
		return type.equals(compare);
	}
	public Boolean getAnswer(String compare){
		return answer.equals(compare);
	}
	
	public void setcontent(String file){
		content = file;
	}
	public void setClue(String clue){
		this.clue = clue;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setAnswer(String answer){
		this.answer = answer;
	}
}