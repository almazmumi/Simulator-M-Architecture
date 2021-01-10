import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramCounter {
	private List<Instruction> instructionsList;
	private HashMap<String, Integer> lableList;
	private int programCounter;
	private boolean isItRunning;
	public ProgramCounter() {
		this.instructionsList = new ArrayList<Instruction>();
		lableList = new HashMap<String, Integer>();
		programCounter = 0;
		isItRunning = false;
	}
	
	public List<Instruction> getInstructionsList() {
		return instructionsList;
	}
	
	public void setInstructionsList(List<Instruction> instructionsList) {
		this.instructionsList = instructionsList;
	}
	
	public int getProgramCounter() {
		return programCounter;
	}
	
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
		isItRunning = true;
	}
	

	public void incrementProgramCounter() {
		// TODO Auto-generated method stub
		programCounter++;
		isItRunning = true;
	
	}

	public int getLableAddress(String lable) {
		return lableList.get(lable);
	}

	public void addLableAddress(String lable, int lableAddress) {
		lableList.put(lable, lableAddress);
	}
	
	public void reset() {
		instructionsList.clear();
		lableList.clear();
		programCounter = 0;
		isItRunning=false;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		for (Integer key : lableList.values()) {
			System.out.println(key);
		}
		return "";
	}

	public boolean isItRunning() {
		return isItRunning;
	}

	public void setItRunning(boolean isItRunning) {
		this.isItRunning = isItRunning;
	}
	
	
	
	
}
