
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
	
	public void execute(ProgramCounter pc, RegisterFile r) {
		registerFile = r;
		if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		}
		pc.incrementProgramCounter();
	}
        /*
        ALU INTSTRUCTIONS (R-FORMAT)
        */
	private void ADD(){
            registerFile.setRegister(d, registerFile.getRegister(a) + registerFile.getRegister(b));
	}
        
        private void AND() {
            registerFile.setRegister(d, registerFile.getRegister(a) & registerFile.getRegister(b));
	}
        
        private void OR() {
            registerFile.setRegister(d, registerFile.getRegister(a) | registerFile.getRegister(b));
	}
        
        private void XOR() {
            registerFile.setRegister(d, registerFile.getRegister(a) ^ registerFile.getRegister(b));
	}
        
        private void NADD() {
            registerFile.setRegister(d, (registerFile.getRegister(a)*-1) + registerFile.getRegister(b));
	}
        
        private void CAND() {
            registerFile.setRegister(d, ~registerFile.getRegister(a) & registerFile.getRegister(b));
	}
        
        private void COR() {
            registerFile.setRegister(d, ~registerFile.getRegister(a) | registerFile.getRegister(b));
	}
        
        private void XNOR() {
            registerFile.setRegister(d, ~registerFile.getRegister(a) ^ registerFile.getRegister(b));
	}
        
         private void SUB() {
             NADD();
	}
        
        private void ANDC() {
            CAND();
	}
        
        private void ORC() {
            COR();
        }
        /*
        ALU INTSTRUCTIONS (R-FORMAT)
        */
        
        /*
        ALU COMPARE INTSTRUCTIONS (R-FORMAT)
        */
	private void EQ() {
            if(registerFile.getRegister(a)==registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void NE() {
           if(registerFile.getRegister(a)!=registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void LT() {
            if(registerFile.getRegister(a)<registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void GE() {
            if(registerFile.getRegister(a)>=registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void LTU() {
            if(registerFile.getRegister(a)<registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void GEU() {
            if(registerFile.getRegister(a)>=registerFile.getRegister(b)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void MIN() {
            if(registerFile.getRegister(a)<registerFile.getRegister(b)){
                registerFile.setRegister(d, registerFile.getRegister(a));
            }else if(registerFile.getRegister(a)>registerFile.getRegister(b)){
                registerFile.setRegister(d, registerFile.getRegister(b));
            }else{
                registerFile.setRegister(d,1);
            }
	}
        
        private void MAX() {
            if(registerFile.getRegister(a)>registerFile.getRegister(b)){
                registerFile.setRegister(d, registerFile.getRegister(a));
            }else if(registerFile.getRegister(a)<registerFile.getRegister(b)){
                registerFile.setRegister(d, registerFile.getRegister(b));
            }else{
                registerFile.setRegister(d,1);
            }
	}
        
         private void GT() {
             LT();
	}
        
        private void LE() {
            GE();
	}
        
        private void GTU() {
            LEU();
        }
        
        private void LEU() {
            GEU();
        }
        /*
        ALU INTSTRUCTIONS (R-FORMAT)
        */
        
         /*
        ALU SHIFT AND ROTATE INTSTRUCTIONS (R-FORMAT)
        */
	private void SHL() {
            int x=registerFile.getRegister(a);
                x=x<<b;
		registerFile.setRegister(d, x);
	}
        
        private void SHR() {
            int x=registerFile.getRegister(a);
                x=x>>>b;
		registerFile.setRegister(d, x);
	}
        
        private void SAR() {
            int x=registerFile.getRegister(a);
                x=x>>b;
		registerFile.setRegister(d, x);
	}
        
        private void ROR() {
            int x=registerFile.getRegister(a);
                x=Integer.rotateRight(x, b);
		registerFile.setRegister(b, x);
	}
        
//        private void SHL() {
//            SHLR();
//	}
//        
//        private void SHR() {
//            SHLR();
//	}
//        
//        private void SAR() {
//            SALR();
//	}
//        
//	private void ROL() {
//            ROR();
//	}
        /*
        ALU SHIFT AND ROTATE INTSTRUCTIONS (R-FORMAT)
        */
        
         /*
        ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
        */
	private void MUL() {
            registerFile.setRegister(d, registerFile.getRegister(a) * registerFile.getRegister(b));
	}
        
        private void DIV() {
            registerFile.setRegister(d, registerFile.getRegister(a) / registerFile.getRegister(b));
	}
        
        private void MOD() {
            registerFile.setRegister(d, registerFile.getRegister(a) % registerFile.getRegister(b));
	}
        
        private void DIVU() {
            registerFile.setRegister(d, registerFile.getRegister(a) / registerFile.getRegister(b));
	}
        
        private void MODU() {
            registerFile.setRegister(d, registerFile.getRegister(a) % registerFile.getRegister(b));
	}
        /*
        ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
        */
	
}

/*
  
        ALU INTSTRUCTIONS (R-FORMAT)
        
	private void ADD(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, registerFile.getRegister(Ra) + registerFile.getRegister(Rb));
	}
        
        private void AND(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, registerFile.getRegister(Ra) & registerFile.getRegister(Rb));
	}
        
        private void OR(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, registerFile.getRegister(Ra) | registerFile.getRegister(Rb));
	}
        
        private void XOR(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, registerFile.getRegister(Ra) ^ registerFile.getRegister(Rb));
	}
        
        private void NADD(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, (registerFile.getRegister(Ra)*-1) + registerFile.getRegister(Rb));
	}
        
        private void CAND(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, ~registerFile.getRegister(Ra) & registerFile.getRegister(Rb));
	}
        
        private void COR(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, ~registerFile.getRegister(Ra) | registerFile.getRegister(Rb));
	}
        
        private void XNOR(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
		registerFile.setRegister(Rd, ~registerFile.getRegister(Ra) ^ registerFile.getRegister(Rb));
	}
        
         private void SUB(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
             NADD(Rd,Rb,Ra);
	}
        
        private void ANDC(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
            CAND(Rd,Rb,Ra);
	}
        
        private void ORC(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
            COR(Rd,Rb,Ra);
        }
        
        ALU INTSTRUCTIONS (R-FORMAT)
        
        
        
        ALU COMPARE INTSTRUCTIONS (R-FORMAT)
        
	private void EQ(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)==registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void NE(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
           if(registerFile.getRegister(Ra)!=registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void LT(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)<registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void GE(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)>=registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void LTU(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)<registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void GEU(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)>=registerFile.getRegister(Rb)){
                registerFile.setRegister(d, 1);
            }else{
                registerFile.setRegister(d, -1);
            }
	}
        
        private void MIN(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)<registerFile.getRegister(Rb)){
                registerFile.setRegister(d, registerFile.getRegister(Ra));
            }else{
                registerFile.setRegister(d, registerFile.getRegister(Rb));
            }
	}
        
        private void MAX(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=a;
            Rb=b;
            if(registerFile.getRegister(Ra)>registerFile.getRegister(Rb)){
                registerFile.setRegister(d, registerFile.getRegister(Ra));
            }else{
                registerFile.setRegister(d, registerFile.getRegister(Rb));
            }
	}
        
         private void GT(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
             LT(Rd,Rb,Ra);
	}
        
        private void LE(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
            GE(Rd,Rb,Ra);
	}
        
        private void GTU(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
            LEU(Rd,Rb,Ra);
        }
        
        private void LEU(int Rd , int Ra , int Rb) {
            Rd=d;
            Ra=b;
            Rb=a;
            GEU(Rd,Rb,Ra);
        }
        
        ALU INTSTRUCTIONS (R-FORMAT)
        
*/