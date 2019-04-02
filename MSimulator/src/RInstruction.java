
public class RInstruction extends Instruction {

	private int a;
	private int b;
	private int x;
	private int f;
	private int c;
	private int d;
	
	DataMemory dataMemory;
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
	
	public void execute(ProgramCounter pc, RegisterFile r, DataMemory M)  {
		registerFile = r;
		dataMemory = M;
		if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();

		}else if(getInstrcutionOpcode() == 40 && f == 1 && x == 0) {
			NADD();
		}else if(getInstrcutionOpcode() == 40 && f == 2 && x == 0) {
			AND();
		}else if(getInstrcutionOpcode() == 40 && f == 3 && x == 0) {
			CAND();
		}else if(getInstrcutionOpcode() == 40 && f == 4 && x == 0) {
			OR();
		}else if(getInstrcutionOpcode() == 40 && f == 5 && x == 0) {
			COR();
		}else if(getInstrcutionOpcode() == 40 && f == 6 && x == 0) {
			XOR();
		}else if(getInstrcutionOpcode() == 40 && f == 7 && x == 0) {
			XNOR();
		}else if(getInstrcutionOpcode() == 40 && f == 8 && x == 0) {
			EQ();
		}else if(getInstrcutionOpcode() == 40 && f == 9 && x == 0) {
			NE();
		}else if(getInstrcutionOpcode() == 40 && f == 10 && x == 0) {
			LT();
		}else if(getInstrcutionOpcode() == 40 && f == 11 && x == 0) {
			GE();
		}else if(getInstrcutionOpcode() == 40 && f == 12 && x == 0) {
			LTU();
		}else if(getInstrcutionOpcode() == 40 && f == 13 && x == 0) {
			GEU();
		}else if(getInstrcutionOpcode() == 40 && f == 14 && x == 0) {
			MIN();
		}else if(getInstrcutionOpcode() == 40 && f == 15 && x == 0) {
			MAX();
		}else if(getInstrcutionOpcode() == 40 && f == 0 && x == 1) {
			SHL();
		}else if(getInstrcutionOpcode() == 40 && f == 1 && x == 1) {
			SHR();
		}else if(getInstrcutionOpcode() == 40 && f == 2 && x == 1) {
			SAR();
		}else if(getInstrcutionOpcode() == 40 && f == 3 && x == 1) {
			ROR();
		}else if(getInstrcutionOpcode() == 40 && f == 8 && x == 1) {
			MUL();
		}else if(getInstrcutionOpcode() == 40 && f == 12 && x == 1) {
			DIV();
		}else if(getInstrcutionOpcode() == 40 && f == 13 && x == 1) {
			MOD();
		}else if(getInstrcutionOpcode() == 40 && f == 14 && x == 1) {
			DIVU();
		}else if(getInstrcutionOpcode() == 40 && f == 15 && x == 1) {
			MODU();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		}else if(getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
//>>>>>>> 528c308f2f164054bb624bccd901ca4be5b2ff6c
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
        
//        private void SUB() {
//             NADD();
//	}
//        
//        private void ANDC() {
//            CAND();
//	}
//        
//        private void ORC() {
//            COR();
//        }
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
