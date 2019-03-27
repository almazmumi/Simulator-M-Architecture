
public class BInstruction extends Instruction {

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
	
	public void execute(ProgramCounter pc, RegisterFile rf) {
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
	private void BGEUI() {
		// TODO Auto-generated method stub
		
	}
	private void BLTUI() {
		// TODO Auto-generated method stub
		
	}
	private void BGEI() {
		// TODO Auto-generated method stub
		
	}
	private void BLTI() {
		// TODO Auto-generated method stub
		
	}
	private void BNEI() {
		// TODO Auto-generated method stub
		
	}
	private void BEQI() {
		// TODO Auto-generated method stub
		
	}
	private void JR() {
		// TODO Auto-generated method stub
		
	}
	private void JALR() {
		// TODO Auto-generated method stub
		
	}
	private void LOOPD() {
		// TODO Auto-generated method stub
		
	}
	private void LOOP() {
		// TODO Auto-generated method stub
		
	}
	private void BGEU() {
		if(rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	private void BLTU() {
		
		// Make Sure that is extended U not S signed
		if(rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	private void BGE() {
		if(rf.getRegister(a) >= rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	private void BLT() {
		if(rf.getRegister(a) < rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	private void BNE() {
		if(rf.getRegister(a) != rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	private void BEQ() {
		if(rf.getRegister(a) == rf.getRegister(b)) {
			pc.setProgramCounter(offset);
		}
		
	}
	

	
	
	
	
	
}
