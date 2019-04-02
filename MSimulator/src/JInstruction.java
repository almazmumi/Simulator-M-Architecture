
public class JInstruction extends Instruction{

	private int offset;
	public JInstruction(int instructionNumber, String instructionName, String instructionBinary) {
		super(instructionNumber, instructionName, instructionBinary);
		// TODO Auto-generated constructor stub
		offset = Integer.parseInt(instructionBinary.substring(6),2);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 6),2));
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	public void execute(ProgramCounter pc, RegisterFile r,DataMemory M)  {
		if(getInstrcutionOpcode() == 2) {
			J(pc);
		}else if(getInstrcutionOpcode() == 3) {
			JAL(pc, r);
		}
		
	}
	
	public void J(ProgramCounter pc) {
		pc.setProgramCounter(offset);
	}
	
	public void JAL(ProgramCounter pc, RegisterFile r) {
		pc.setProgramCounter(offset);
		r.setRegister(31, pc.getProgramCounter()+1);
	}

	
	
}
