
public class BInstruction extends Instruction {
	//TODO - Error handling
	private int a;
	private int b;
	private int offset;
	private ProgramCounter pc;
	private RegisterFile rf;
	public BInstruction(int instructionNumber, String instructionText, String instructionBinary) {
		super(instructionNumber, instructionText, instructionBinary);
		a = Integer.parseInt(instructionBinary.substring(6, 11),2);
		b = Integer.parseInt(instructionBinary.substring(11, 16),2);
		offset = Integer.parseInt(instructionBinary.substring(16, 32),2);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0,6), 2));
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
	
	public void execute(ProgramCounter pc, RegisterFile r,Memory M)  {
		this.pc = pc;
		this.rf = rf;
		
		if(getInstrcutionOpcode() == 16) {
			BEQ();
		}else if(getInstrcutionOpcode() == 17) {
			BNE();
		}else if(getInstrcutionOpcode() == 18) {
			BLT();
		}else if(getInstrcutionOpcode() == 19) {
			BGE();
		}else if(getInstrcutionOpcode() == 20) {
			BLTU();
		}else if(getInstrcutionOpcode() == 21) {
			BGEU();
		}else if(getInstrcutionOpcode() == 22) {
			LOOP();
		}else if(getInstrcutionOpcode() == 23) {
			LOOPD();
		}else if(getInstrcutionOpcode() == 15) {
			JALR();
		}else if(getInstrcutionOpcode() == 14) {
			JR();
		}else if(getInstrcutionOpcode() == 8) {
			BEQI();
		}else if(getInstrcutionOpcode() == 9) {
			BNEI();
		}else if(getInstrcutionOpcode() == 10) {
			BLTI();
		}else if(getInstrcutionOpcode() == 11) {
			BGEI();
		}else if(getInstrcutionOpcode() == 12) {
			BLTUI();
		}else if(getInstrcutionOpcode() == 13) {
			BGEUI();
		}
	}
	
	
	// ======================================================================
	
	// TODO Unsigned 
	private void BGEUI() {
		if(rf.getRegister(a) >= b) {
			pc.setProgramCounter(offset);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	
	// TODO Unsigned 
	private void BLTUI() {
		if(rf.getRegister(a) < b) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
	}
	private void BGEI() {
		if(rf.getRegister(a) >= b) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BLTI() {
		if(rf.getRegister(a) < b) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BNEI() {
		if(rf.getRegister(a) != b) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BEQI() {
		if(rf.getRegister(a) == b) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	
	// ================================================

	
	private void JR() {
		pc.setProgramCounter(rf.getRegister(b) + offset-1);
	}
	private void JALR() {
		pc.setProgramCounter(rf.getRegister(b)+offset-1);
		rf.setRegister(b, pc.getProgramCounter());
	}
	private void LOOPD() {
		
	}
	private void LOOP() {
		
		
	}
	
	// =================================================
	
	
	// TODO Unsigned 
	private void BGEU() {
		if(rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	
	// TODO Unsigned 
	private void BLTU() {
		
		// Make Sure that is extended U not S signed
		if(rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	
	
	private void BGE() {
		if(rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BLT() {
		if(rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BNE() {
		if(rf.getRegister(a) != rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	private void BEQ() {
		if(rf.getRegister(a) == rf.getRegister(b)) {
			pc.setProgramCounter(offset-1);
		}else {
			pc.incrementProgramCounter();
		}
		
	}
	// ==========================================================

	
	
	
	
	
}
