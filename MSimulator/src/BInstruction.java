
public class BInstruction extends Instruction {

	private String a;
	private String b;
	private String offset;
	public BInstruction(int instructionNumber, String instructionText, String instructionBinary) {
		super(instructionNumber, instructionText, instructionBinary);
		// TODO Auto-generated constructor stub
		a = instructionBinary.substring(6, 10);
		b = instructionBinary.substring(11, 15);
		offset = instructionBinary.substring(16, 31);
		
	}
	public String getA() {
		return a;
	}
	public String getB() {
		return b;
	}
	public String getOffset() {
		return offset;
	}
	
	
	
}
