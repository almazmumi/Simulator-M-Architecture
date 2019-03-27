import java.util.ArrayList;
import java.util.List;

public class ProgramCounter {
	private List<Instruction> instructionsList;
	private int programCounter;
	
	public ProgramCounter() {
		this.instructionsList = new ArrayList<Instruction>();
		programCounter = 0;
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
	}
	


	public void incrementProgramCounter() {
		// TODO Auto-generated method stub
		programCounter++;
	
	}
	
	
	
	
	
	
}
