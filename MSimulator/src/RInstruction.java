
public class RInstruction extends Instruction {

	private int a;
	private int b;
	private int x;
	private int f;
	private int c;
	private int d;
	
	
	private RegisterFile registerFile;
	public RInstruction(int instructionNumber, String instructionName, String instructionBinary) {
		super(instructionNumber, instructionName, instructionBinary);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 5),2));
		a = Integer.parseInt(instructionBinary.substring(6, 11),2);
		b = Integer.parseInt(instructionBinary.substring(11, 16),2);
		f = Integer.parseInt(instructionBinary.substring(16, 20),2);
		x = Integer.parseInt(instructionBinary.substring(20, 22),2);
		c = Integer.parseInt(instructionBinary.substring(22, 27),2);
		d = Integer.parseInt(instructionBinary.substring(27, 32),2);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 6),2));
		registerFile = new RegisterFile();
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getX() {
		return x;
	}

	public int getF() {
		return f;
	}

	public int getC() {
		return c;
	}

	public int getD() {
		return d;
	}
	
	public void execute(RegisterFile r) {
		registerFile = r;
		if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			add();
		}
	}

	private void add() {
		registerFile.setRegister(d, registerFile.getRegister(a) + registerFile.getRegister(b));
	}

	
}
