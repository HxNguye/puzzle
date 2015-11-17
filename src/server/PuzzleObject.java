package server;

public class PuzzleObject {
	String fileName;
	String clue;
	int stageNumber;
	String type;
	String answer;
	
	PuzzleObject(){
		
	}
	
	PuzzleObject(String file, String answer, String clue, String type, int stage){
		fileName = file;
		this.clue = clue;
		stageNumber = stage;
		this.type = type;
		this.answer = answer;
	}
	
	public String getFileName(){
		return fileName;
	}
	public String getClue(){
		return clue;
	}
	public String getType(){
		return type;
	}
	public String getAnswer(){
		return answer;
	}
	public int getStageNubmer(){
		return stageNumber;
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
	public void setStageNubmer(int stageNumber){
		this.stageNumber = stageNumber;
	}
}