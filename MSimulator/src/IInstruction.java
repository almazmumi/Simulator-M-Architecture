
public class IInstruction extends Instruction{
	
	private int a;
	private int b;
	private int f;
	private int imm12;
	private int l;
	private int r;
	
	RegisterFile registerFile;
	public IInstruction(int instructionNumber, String instructionText, String instructionBinary) {
		super(instructionNumber, instructionText, instructionBinary);
		a = Integer.parseInt(instructionBinary.substring(6, 11),2);
		b = Integer.parseInt(instructionBinary.substring(11, 16),2);
		String fS =instructionBinary.substring(16, 20); 
		f = Integer.parseInt(fS,2);
		imm12 = Integer.parseInt(instructionBinary.substring(20, 32),2);
		String opcodeS = instructionBinary.substring(0, 6);
		int opcode = Integer.parseInt(opcodeS,2);
		setInstructionOpcode(opcode);
		

	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getF() {
		return f;
	}


	public int getImm12() {
		return imm12;
	}
	
	public void execute(RegisterFile r) {
		registerFile = r;
		if(getInstrcutionOpcode() == 32 && f == 0) {
			add();
		}
	}

	private void add() {
		int result  = registerFile.getRegister(a) + imm12;
		registerFile.setRegister(b,result );
	}



	

}
