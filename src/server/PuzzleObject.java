package server;

public class PuzzleObject {
	String fileName;
	String clue;
	String type;
	String answer;
	
	PuzzleObject(){
		
	}
	
	PuzzleObject(String file, String answer, String clue, String type){
		fileName = file;
		this.clue = clue;
		this.type = type;
		this.answer = answer;
	}
	
	public Boolean getFileName(String compare){
		return fileName.equals(compare);
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
	
	public void setFileName(String file){
		fileName = file;
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