
public class IInstruction extends Instruction{
	
	private String a;
	private String b;
	private String f;
	private String imm12;
	
	public IInstruction(int instructionNumber, String instructionText, String instructionBinary) {
		super(instructionNumber, instructionText, instructionBinary);
		a = instructionBinary.substring(6, 10);
		b = instructionBinary.substring(11, 15);
		f = instructionBinary.substring(16, 19);
		imm12 = instructionBinary.substring(20, 31);

	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public String getF() {
		return f;
	}

	public String getImm12() {
		return imm12;
	}

	

}
