import javax.swing.JEditorPane;

public class Instruction {
	private int instructionNumber;
	private String instructionName;
	private String instructionBinary;
	private int instrcutionOpcode;
	private boolean isExecuting;
	
	public Instruction(int instructionNumber,String instructionName,String instructionBinary) {
		this.instructionBinary = instructionBinary;
		this.instructionName = instructionName;
		this.instructionNumber = instructionNumber;
		setExecuting(false);
	}


	public int getInstrcutionOpcode() {
		return instrcutionOpcode;
	}

	public void setInstructionOpcode(int instrcutionOpcode) {
		this.instrcutionOpcode = instrcutionOpcode;
	}

	public void setInstructionName(String instructionName) {
		this.instructionName = instructionName;
	}

	public int getInstructionNumber() {
		return instructionNumber;
	}

	public void setInstructionNumber(int instructionNumber) {
		this.instructionNumber = instructionNumber;
	}

	public String getInstructionName() {
		return instructionName;
	}

	public void setInstructionText(String instructionName) {
		this.instructionName = instructionName;
	}

	public String getInstructionBinary() {
		return instructionBinary;
	}

	public void setInstructionBinary(String instructionBinary) {
		this.instructionBinary = instructionBinary;
	}
	
	public void execute(ProgramCounter pc, RegisterFile r, DataMemory M, JEditorPane iOEditorPane) {
		System.out.println("noooo");
	}
	
	public void execute(ProgramCounter pc, RegisterFile r, DataMemory M) {
		System.out.println("noooo");
	}

	public boolean isExecuting() {
		return isExecuting;
	}


	public void setExecuting(boolean isExecuting) {
		this.isExecuting = isExecuting;
	}

	
	
	

}
