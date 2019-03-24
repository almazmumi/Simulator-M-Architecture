
public class RInstruction extends Instruction {

	private String a;
	private String bORimm5;
	private String x;
	private String f;
	private String c;
	private String d;
	
	public RInstruction(int instructionNumber, String instructionName, String instructionBinary) {
		super(instructionNumber, instructionName, instructionBinary);
		a = instructionBinary.substring(6, 10);
		bORimm5 = instructionBinary.substring(11, 15);
		f = instructionBinary.substring(16, 19);
		x = instructionBinary.substring(20, 21);
		c = instructionBinary.substring(22, 26);
		d = instructionBinary.substring(27, 31);
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return bORimm5;
	}

	public String getX() {
		return x;
	}

	public String getF() {
		return f;
	}

	public String getC() {
		return c;
	}

	public String getD() {
		return d;
	}

	
}
