
public class BInstruction extends Instruction {
	private int a;
	private int b;
	private int offset;
	private ProgramCounter pc;
	private RegisterFile rf;

	public BInstruction(int instructionNumber, String instructionText, String instructionBinary) {
		super(instructionNumber, instructionText, instructionBinary);
		a = Integer.parseInt(instructionBinary.substring(6, 11), 2);
		b = Integer.parseInt(instructionBinary.substring(11, 16), 2);
		offset = returnSigned(instructionBinary.substring(16, 32));
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 6), 2));
	}

	public int returnSigned(String S) {
		int signed;
		int number;
		if (S.charAt(0) == '1')
			signed = -32768;
		else
			signed = 0;

		number = Integer.parseInt(S.substring(1), 2);
		return number + signed;
	}

	
	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getOffset() {
		return offset;
	}

	public void execute() {
		this.pc = GlobalVariables.pc;
		this.rf = GlobalVariables.rf;

		if (getInstrcutionOpcode() == 16) {
			BEQ();
		} else if (getInstrcutionOpcode() == 17) {
			BNE();
		} else if (getInstrcutionOpcode() == 18) {
			BLT();
		} else if (getInstrcutionOpcode() == 19) {
			BGE();
		} else if (getInstrcutionOpcode() == 20) {
			BLTU();
		} else if (getInstrcutionOpcode() == 21) {
			BGEU();
		} else if (getInstrcutionOpcode() == 22) {
			LOOP();
		} else if (getInstrcutionOpcode() == 23) {
			LOOPD();
		} else if (getInstrcutionOpcode() == 15) {
			JALR();
		} else if (getInstrcutionOpcode() == 14) {
			JR();
		} else if (getInstrcutionOpcode() == 8) {
			BEQI();
		} else if (getInstrcutionOpcode() == 9) {
			BNEI();
		} else if (getInstrcutionOpcode() == 10) {
			BLTI();
		} else if (getInstrcutionOpcode() == 11) {
			BGEI();
		} else if (getInstrcutionOpcode() == 12) {
			BLTUI();
		} else if (getInstrcutionOpcode() == 13) {
			BGEUI();
		}
	}

	private void BGEUI() {
		
		if (rf.getRegister(a) >= b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BLTUI() {
		if (rf.getRegister(a) < b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}
	}

	private void BGEI() {
		if (rf.getRegister(a) >= b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BLTI() {
		if (rf.getRegister(a) < b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BNEI() {
		if (rf.getRegister(a) != b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BEQI() {
		if (rf.getRegister(a) == b) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	// ================================================

	private void JR() {
		pc.setProgramCounter((int) rf.getRegister(b));
	}

	private void JALR() {
		pc.setProgramCounter((int) rf.getRegister(b));
		rf.setRegister(b, pc.getProgramCounter());
	}

	private void LOOPD() {
		rf.setRegister(b, rf.getRegister(b) - 1);
		if (rf.getRegister(b) == rf.getRegister(a)) {
			pc.incrementProgramCounter();
		} else {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		}
	}

	private void LOOP() {
		rf.setRegister(b, rf.getRegister(b) + 1);
		if (rf.getRegister(b) == rf.getRegister(a)) {
			pc.incrementProgramCounter();
		} else {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		}

	}

	// =================================================

	private void BGEU() {
		if (rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BLTU() {

		if (rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BGE() {
		if (rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BLT() {
		if (rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BNE() {
		if (rf.getRegister(a) != rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}

	private void BEQ() {
		if (rf.getRegister(a) == rf.getRegister(b)) {
			pc.setProgramCounter(pc.getProgramCounter() + offset);
		} else {
			pc.incrementProgramCounter();
		}

	}
	// ==========================================================

}
