
public class JInstruction extends Instruction{

	private String offset;
	public JInstruction(int instructionNumber, String instructionName, String instructionBinary) {
		super(instructionNumber, instructionName, instructionBinary);
		// TODO Auto-generated constructor stub
		offset = instructionBinary.substring(6);
	}
	
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}

	
}
