
public class Instruction {
	private int instructionNumber;
	private String instructionName;
	private String instructionBinary;
	private String instrcutionOpcode;
	
	public Instruction(int instructionNumber,String instructionName,String instructionBinary) {
		this.instructionBinary = instructionBinary;
		this.instructionName = instructionName;
		this.instructionNumber = instructionNumber;
	}


	public String getInstrcutionOpcode() {
		return instrcutionOpcode;
	}

	public void setInstrcutionOpcode(String instrcutionOpcode) {
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

	
	

}
