
public class IInstruction extends Instruction{
	
	private int a;
	private int b;
	private int f;
	private int imm12;
	private int l;
	private int r;
	Memory Mem;
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
	
	public void execute(ProgramCounter pc, RegisterFile r,Memory M) {
        registerFile = r;
        Mem=M;
		if(getInstrcutionOpcode() == 32 && f == 0) {
			ADD();
<<<<<<< HEAD
		}else if(getInstrcutionOpcode() == 32 && f == 1) {
			NADD();
		}else if(getInstrcutionOpcode() == 32 && f == 2) {
			AND();
		}else if(getInstrcutionOpcode() == 32 && f == 3) {
			CAND();
		}else if(getInstrcutionOpcode() == 33 && f == 4) {
			OR();
		}else if(getInstrcutionOpcode() == 33 && f == 5) {
			COR();
		}else if(getInstrcutionOpcode() == 33 && f == 6) {
			XOR();
		}else if(getInstrcutionOpcode() == 33 && f == 7) {
			SET();
		}else if(getInstrcutionOpcode() == 34 && f == 8) {
			EQ();
		}else if(getInstrcutionOpcode() == 34 && f == 9) {
			NE();
		}else if(getInstrcutionOpcode() == 34 && f == 10) {
			LT();
		}else if(getInstrcutionOpcode() == 34 && f == 11) {
			GE();
		}else if(getInstrcutionOpcode() == 35 && f == 12) {
			LTU();
		}else if(getInstrcutionOpcode() == 35 && f == 13) {
			GEU();
		}else if(getInstrcutionOpcode() == 35 && f == 14) {
			MIN();
		}else if(getInstrcutionOpcode() == 35 && f == 15) {
			MAX();
		}else if(getInstrcutionOpcode() == 37 && f == 0) {
			SHLR();
		}else if(getInstrcutionOpcode() == 37 && f == 1) {
			SHLR();
		}else if(getInstrcutionOpcode() == 37 && f == 2) {
			SALR();
		}else if(getInstrcutionOpcode() == 37 && f == 3) {
			ROR();
		}else if(getInstrcutionOpcode() == 37 && f == 8) {
			MUL();
		}else if(getInstrcutionOpcode() == 37 && f == 12) {
			DIV();
		}else if(getInstrcutionOpcode() == 37 && f == 13) {
			MOD();
		}else if(getInstrcutionOpcode() == 37 && f == 14) {
			DIVU();
		}else if(getInstrcutionOpcode() == 37 && f == 15) {
			MODU();
		}else if(getInstrcutionOpcode() == 25 && f == 0) {
            SB();
        }else if(getInstrcutionOpcode() == 25 && f == 1) {
            SH();
        }else if(getInstrcutionOpcode() == 25 && f == 2) {
            SW();
        }else if(getInstrcutionOpcode() == 25 && f == 3) {
            SD();
        }else if(getInstrcutionOpcode() == 24 && f == 0) {
            
        }else if(getInstrcutionOpcode() == 24 && f == 1) {
            
        }else if(getInstrcutionOpcode() == 24 && f == 2) {
            
        }else if(getInstrcutionOpcode() == 24 && f == 3) {
            
        }else if(getInstrcutionOpcode() == 24 && f == 4) {
            LB();
        }else if(getInstrcutionOpcode() == 24 && f == 5) {
            LH();
        }else if(getInstrcutionOpcode() == 24 && f == 6) {
            LW();
        }else if(getInstrcutionOpcode() == 24 && f == 7) {
            LD();
        }






		pc.setProgramCounter(pc.getProgramCounter()+1);
	}
        /*
        ALU INTSTRUCTIONS (I-FORMAT)
        */
	private void ADD() {
            registerFile.setRegister(b, registerFile.getRegister(a) + imm12);
	}
        
        private void AND() {
            registerFile.setRegister(b, registerFile.getRegister(a) & imm12);
	}
        
        private void OR() {
            registerFile.setRegister(b, registerFile.getRegister(a) | imm12);
	}
        
        private void XOR() {
            registerFile.setRegister(b, registerFile.getRegister(a) ^ imm12);
	}
        
        private void NADD() {
            registerFile.setRegister(b, (registerFile.getRegister(a)*-1) + imm12);
	}
        
        private void CAND() {
            registerFile.setRegister(b, ~registerFile.getRegister(a) & imm12);
	}
        
        private void COR() {
            registerFile.setRegister(b, ~registerFile.getRegister(a) | imm12);
	}
        
        private void SET() {
            registerFile.setRegister(b,imm12);
	}
        
         private void SUB() {
             ADD();
	}
        
        private void ANDC() {
            ADD();
	}
        
        private void ORC() {
            OR();
        }
        
          private void XNOR() {
             XOR();
	}
        
        private void MOV() {
            OR();
	}
        
        private void NEG() {
            NADD();
        }
        
        private void NOT() {
            COR();
        }
        /*
        ALU INTSTRUCTIONS (I-FORMAT)
        */
        
        /*
        ALU COMPARE INTSTRUCTIONS (I-FORMAT)
        */
	private void EQ() {
            if(registerFile.getRegister(a)==imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void NE() {
           if(registerFile.getRegister(a)!=imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void LT() {
            if(registerFile.getRegister(a)<imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void GE() {
            if(registerFile.getRegister(a)>=imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void LTU() {
            if(registerFile.getRegister(a)<imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void GEU() {
            if(registerFile.getRegister(a)>=imm12){
                registerFile.setRegister(b, 1);
            }else{
                registerFile.setRegister(b, -1);
            }
	}
        
        private void MIN() {
            if(registerFile.getRegister(a)<imm12){
                registerFile.setRegister(b, registerFile.getRegister(a));
            }else if(registerFile.getRegister(a)>imm12){
                registerFile.setRegister(b, imm12);
            }else{
                registerFile.setRegister(b,1);
            }
	}
        
        private void MAX() {
            if(registerFile.getRegister(a)>imm12){
                registerFile.setRegister(b, registerFile.getRegister(a));
            }else if(registerFile.getRegister(a)<imm12){
                registerFile.setRegister(b, imm12);
            }else{
                registerFile.setRegister(b,1);
            }
	}
        
         private void GT() {
             GE();
	}
        
        private void LE() {
            LT();
	}
        
        private void GTU() {
            GEU();
        }
        
        private void LEU() {
            LEU();
        }
        /*
        ALU INTSTRUCTIONS (I-FORMAT)
        */
        
        /*
        ALU SHIFT AND ROTATE INTSTRUCTIONS (I-FORMAT)
        */
	private void SHLR() {
            int x=registerFile.getRegister(a);
                x=x<<l;
                x=x>>>r;
		registerFile.setRegister(b, x);
	}
        
        private void SALR() {
            int x=registerFile.getRegister(a);
                x=x<<l;
                x=x>>r;
		registerFile.setRegister(b, x);
	}
        
        private void ROR() {
            int x=registerFile.getRegister(a);
                x=Integer.rotateRight(x, r);
		registerFile.setRegister(b, x);
	}
        
        private void SHL() {
            SHLR();
	}
        
        private void SHR() {
            SHLR();
	}
        
        private void SAR() {
            SALR();
	}
        
	private void ROL() {
            ROR();
	}
        /*
        ALU SHIFT AND ROTATE INTSTRUCTIONS (I-FORMAT)
        */
        
        /*
        ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
        */
	private void MUL() {
            registerFile.setRegister(b, registerFile.getRegister(a) * imm12);
	}
        
        private void DIV() {
            registerFile.setRegister(b, registerFile.getRegister(a) / imm12);
	}
        
        private void MOD() {
            registerFile.setRegister(b, registerFile.getRegister(a) % imm12);
	}
        
        private void DIVU() {
            registerFile.setRegister(b, registerFile.getRegister(a) / imm12);
	}
        
        private void MODU() {
            registerFile.setRegister(b, registerFile.getRegister(a) % imm12);
	}
        /*
        ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
        */


           private void SB() {
            String Byte = Integer.toBinaryString(registerFile.getRegister(b));
            String d = "";
            String Z = "0";
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
        
            Byte=d+Byte;
            Byte=Byte.substring(56,64);
            System.out.println(Byte);
            System.out.println(Integer.parseInt(Byte,2));
            char Data = (char) Integer.parseInt(Byte,2);
            int Address= imm12+registerFile.getRegister(a);
            Mem.AddData(Address, Data);
            
    }
        
        private void SH() {
            String Byte = Integer.toBinaryString(registerFile.getRegister(b));
            String [] Bytes=new String[2];
            String d = "";
            String Z = "0";
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
        
            Byte=d+Byte;

            Bytes[1]=Byte.substring(48,56);
            Bytes[0]=Byte.substring(56,64);

            for(int i =0;i<2;i++) {
            
            char Data = (char) Integer.parseInt(Bytes[i],2);
            int Address= imm12+registerFile.getRegister(a)+i;
            Mem.AddData(Address, Data);
            }
    }
        private void SW() {
            String Byte = Integer.toBinaryString(registerFile.getRegister(b));
            String [] Bytes=new String[4];
            String d = "";
            String Z = "0";
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
        
            Byte=d+Byte;
            Bytes[3]=Byte.substring(32,40);
            Bytes[2]=Byte.substring(40,48);
            Bytes[1]=Byte.substring(48,56);
            Bytes[0]=Byte.substring(56,64);
            for(int i =0;i<4;i++) {
            char Data = (char) Integer.parseInt(Bytes[i],2);
            int Address= imm12+registerFile.getRegister(a)+i;
            Mem.AddData(Address, Data);
            }
            
    }
        private void SD() {
            String Byte = Integer.toBinaryString(registerFile.getRegister(b));
            String [] Bytes=new String[8];
            String d = "";
            String Z = "0";
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
        
            Byte=d+Byte;
            Bytes[7]=Byte.substring(0,8);
            Bytes[6]=Byte.substring(8,16);
            Bytes[5]=Byte.substring(16,24);
            Bytes[4]=Byte.substring(24,32);
            Bytes[3]=Byte.substring(32,40);
            Bytes[2]=Byte.substring(40,48);
            Bytes[1]=Byte.substring(48,56);
            Bytes[0]=Byte.substring(56,64);

            for(int i =0;i<7;i++) {
            char Data = (char) Integer.parseInt(Bytes[i],2);
            int Address= imm12+registerFile.getRegister(a)+i;
            Mem.AddData(Address, Data);
            }
            
    }
        
        private void LB() {
            

        

            int Address= imm12+registerFile.getRegister(a);
        
            String Byte = Integer.toBinaryString((int)Mem.GetData(Address));
            
            for (int i = 0; i < 8 - Byte.length(); i++) {
                Byte = "0" + Byte;
            }
            
            String d = "";
            String Z = Byte.substring(1,2);
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
            Byte=d+Byte;
             registerFile.setRegister(b,Integer.parseInt(Byte,2));
            
    }
        
        private void LH() {
            String [] Bytes=new String[2];
            int Address= imm12+registerFile.getRegister(a);
        
            String Byte ="";
            Bytes[0]= Integer.toBinaryString((int)Mem.GetData(Address));
            Bytes[1]= Integer.toBinaryString((int)Mem.GetData(Address+1));
            
            for(int j=0;j<Bytes.length;j++) {
            for (int i = 0; i < 8 - Bytes[j].length(); i++) {
                Bytes[j] = "0" + Bytes[j];
            }}
            
            Byte=Bytes[1]+Bytes[0];

            String d = "";
            String Z = Byte.substring(1,2);
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
            Byte=d+Byte;
             registerFile.setRegister(b,Integer.parseInt(Byte,2));
            
    }
        private void LW() {
            String [] Bytes=new String[4];
            int Address= imm12+registerFile.getRegister(a);
        
            String Byte ="";
            Bytes[0]= Integer.toBinaryString((int)Mem.GetData(Address));
            Bytes[1]= Integer.toBinaryString((int)Mem.GetData(Address+1));
            Bytes[2]= Integer.toBinaryString((int)Mem.GetData(Address+2));
            Bytes[3]= Integer.toBinaryString((int)Mem.GetData(Address+3));
            
            for(int j=0;j<Bytes.length;j++) {
            for (int i = 0; i < 8 - Bytes[j].length(); i++) {
                Bytes[j] = "0" + Bytes[j];
            }}
            
            Byte=Bytes[3]+Bytes[2]+Bytes[1]+Bytes[0];
            
            String d = "";
            String Z = Byte.substring(1,2);
            for (int i = 0; i < 64 - Byte.length(); i++) {
                d = d + Z;
            }
            Byte=d+Byte;
             registerFile.setRegister(b,Integer.parseInt(Byte,2));
    }
        private void LD() {
            String [] Bytes=new String[2];
            int Address= imm12+registerFile.getRegister(a);
        
            String Byte ="";
            Bytes[0]= Integer.toBinaryString((int)Mem.GetData(Address));
            Bytes[1]= Integer.toBinaryString((int)Mem.GetData(Address+1));
            Bytes[2]= Integer.toBinaryString((int)Mem.GetData(Address+2));
            Bytes[3]= Integer.toBinaryString((int)Mem.GetData(Address+3));
            Bytes[4]= Integer.toBinaryString((int)Mem.GetData(Address));
            Bytes[5]= Integer.toBinaryString((int)Mem.GetData(Address+1));
            Bytes[6]= Integer.toBinaryString((int)Mem.GetData(Address+2));
            Bytes[7]= Integer.toBinaryString((int)Mem.GetData(Address+3));
            
            for(int j=0;j<Bytes.length;j++) {
            for (int i = 0; i < 8 - Bytes[j].length(); i++) {
                Bytes[j] = "0" + Bytes[j];
            }
            Byte=Bytes[j]+Byte;
            }
            

             registerFile.setRegister(b,Integer.parseInt(Byte,2));
            
    }


}

