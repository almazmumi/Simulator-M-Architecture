
public class RInstruction extends Instruction {

	private int a;
	private int b;
	private int x;
	private int f;
	private int c;
	private int d;

	DataMemory Mem;
	private RegisterFile registerFile;

	public RInstruction(int instructionNumber, String instructionName, String instructionBinary) {
		super(instructionNumber, instructionName, instructionBinary);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 5), 2));
		a = Integer.parseInt(instructionBinary.substring(6, 11), 2);
		b = Integer.parseInt(instructionBinary.substring(11, 16), 2);
		f = Integer.parseInt(instructionBinary.substring(16, 20), 2);
		x = Integer.parseInt(instructionBinary.substring(20, 22), 2);
		c = Integer.parseInt(instructionBinary.substring(22, 27), 2);
		d = Integer.parseInt(instructionBinary.substring(27, 32), 2);
		setInstructionOpcode(Integer.parseInt(instructionBinary.substring(0, 6), 2));
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

	public void execute(ProgramCounter pc, RegisterFile r, DataMemory M) {
		registerFile = r;
		Mem = M;
		if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();

		} else if (getInstrcutionOpcode() == 40 && f == 1 && x == 0) {
			NADD();
		} else if (getInstrcutionOpcode() == 40 && f == 2 && x == 0) {
			AND();
		} else if (getInstrcutionOpcode() == 40 && f == 3 && x == 0) {
			CAND();
		} else if (getInstrcutionOpcode() == 40 && f == 4 && x == 0) {
			OR();
		} else if (getInstrcutionOpcode() == 40 && f == 5 && x == 0) {
			COR();
		} else if (getInstrcutionOpcode() == 40 && f == 6 && x == 0) {
			XOR();
		} else if (getInstrcutionOpcode() == 40 && f == 7 && x == 0) {
			XNOR();
		} else if (getInstrcutionOpcode() == 40 && f == 8 && x == 0) {
			EQ();
		} else if (getInstrcutionOpcode() == 40 && f == 9 && x == 0) {
			NE();
		} else if (getInstrcutionOpcode() == 40 && f == 10 && x == 0) {
			LT();
		} else if (getInstrcutionOpcode() == 40 && f == 11 && x == 0) {
			GE();
		} else if (getInstrcutionOpcode() == 40 && f == 12 && x == 0) {
			LTU();
		} else if (getInstrcutionOpcode() == 40 && f == 13 && x == 0) {
			GEU();
		} else if (getInstrcutionOpcode() == 40 && f == 14 && x == 0) {
			MIN();
		} else if (getInstrcutionOpcode() == 40 && f == 15 && x == 0) {
			MAX();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 1) {
			SHL();
		} else if (getInstrcutionOpcode() == 40 && f == 1 && x == 1) {
			SHR();
		} else if (getInstrcutionOpcode() == 40 && f == 2 && x == 1) {
			SAR();
		} else if (getInstrcutionOpcode() == 40 && f == 3 && x == 1) {
			ROR();
		} else if (getInstrcutionOpcode() == 40 && f == 8 && x == 1) {
			MUL();
		} else if (getInstrcutionOpcode() == 40 && f == 12 && x == 1) {
			DIV();
		} else if (getInstrcutionOpcode() == 40 && f == 13 && x == 1) {
			MOD();
		} else if (getInstrcutionOpcode() == 40 && f == 14 && x == 1) {
			DIVU();
		} else if (getInstrcutionOpcode() == 40 && f == 15 && x == 1) {
			MODU();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
		} else if (getInstrcutionOpcode() == 40 && f == 0 && x == 0) {
			ADD();
//>>>>>>> 528c308f2f164054bb624bccd901ca4be5b2ff6c
		} else if (getInstrcutionOpcode() == 27 && f == 0) {
			SB();
		} else if (getInstrcutionOpcode() == 27 && f == 1) {
			SH();
		} else if (getInstrcutionOpcode() == 27 && f == 2) {
			SW();
		} else if (getInstrcutionOpcode() == 27 && f == 3) {
			SD();
		} else if (getInstrcutionOpcode() == 26 && f == 0) {
			LBU();
		} else if (getInstrcutionOpcode() == 26 && f == 1) {
			LHU();
		} else if (getInstrcutionOpcode() == 26 && f == 2) {
			LWU();
		} else if (getInstrcutionOpcode() == 26 && f == 3) {
			LDU();
		} else if (getInstrcutionOpcode() == 26 && f == 4) {
			LB();
		} else if (getInstrcutionOpcode() == 26 && f == 5) {
			LH();
		} else if (getInstrcutionOpcode() == 26 && f == 6) {
			LW();
		} else if (getInstrcutionOpcode() == 26 && f == 7) {
			LD();
		}
		pc.incrementProgramCounter();
	}

	/*
	 * ALU INTSTRUCTIONS (R-FORMAT)
	 */
	private void ADD() {
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
		registerFile.setRegister(d, (registerFile.getRegister(a) * -1) + registerFile.getRegister(b));
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


	/*
	 * ALU INTSTRUCTIONS (R-FORMAT)
	 */

	/*
	 * ALU COMPARE INTSTRUCTIONS (R-FORMAT)
	 */
	private void EQ() {
		if (registerFile.getRegister(a) == registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void NE() {
		if (registerFile.getRegister(a) != registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void LT() {
		if (registerFile.getRegister(a) < registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void GE() {
		if (registerFile.getRegister(a) >= registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void LTU() {
		if (registerFile.getRegister(a) < registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void GEU() {
		if (registerFile.getRegister(a) >= registerFile.getRegister(b)) {
			registerFile.setRegister(d, 1);
		} else {
			registerFile.setRegister(d, -1);
		}
	}

	private void MIN() {
		if (registerFile.getRegister(a) < registerFile.getRegister(b)) {
			registerFile.setRegister(d, registerFile.getRegister(a));
		} else if (registerFile.getRegister(a) > registerFile.getRegister(b)) {
			registerFile.setRegister(d, registerFile.getRegister(b));
		} else {
			registerFile.setRegister(d, 1);
		}
	}

	private void MAX() {
		if (registerFile.getRegister(a) > registerFile.getRegister(b)) {
			registerFile.setRegister(d, registerFile.getRegister(a));
		} else if (registerFile.getRegister(a) < registerFile.getRegister(b)) {
			registerFile.setRegister(d, registerFile.getRegister(b));
		} else {
			registerFile.setRegister(d, 1);
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
	 * ALU INTSTRUCTIONS (R-FORMAT)
	 */

	/*
	 * ALU SHIFT AND ROTATE INTSTRUCTIONS (R-FORMAT)
	 */
	private void SHL() {
		long x = registerFile.getRegister(a);
		x = x << b;
		registerFile.setRegister(d, x);
	}

	private void SHR() {
		long x = registerFile.getRegister(a);
		x = x >>> b;
		registerFile.setRegister(d, x);
	}

	private void SAR() {
		long x = registerFile.getRegister(a);
		x = x >> b;
		registerFile.setRegister(d, x);
	}

	private void ROR() {
		long x = registerFile.getRegister(a);
		x = Long.rotateRight(x, b);
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
	 * ALU SHIFT AND ROTATE INTSTRUCTIONS (R-FORMAT)
	 */

	/*
	 * ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
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
	 * ALU MULTIPLY AND DIVIDE INTSTRUCTIONS (I-FORMAT)
	 */

	private void SB() {
		String Byte = Long.toBinaryString(registerFile.getRegister(d));
		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}

		Byte = ss + Byte;
		Byte = Byte.substring(56, 64);
		System.out.println(Byte);
		System.out.println(Integer.parseInt(Byte, 2));
		char Data = (char) Integer.parseInt(Byte, 2);
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));
		Mem.addData(Address, Data);

	}

	private void SH() {
		String Byte = Long.toBinaryString(registerFile.getRegister(d));
		String[] Bytes = new String[2];
		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}

		Byte = ss + Byte;

		Bytes[1] = Byte.substring(48, 56);
		Bytes[0] = Byte.substring(56, 64);

		for (int i = 0; i < 2; i++) {

			char Data = (char) Integer.parseInt(Bytes[i], 2);
			int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a) + i);
			Mem.addData(Address, Data);
		}
	}

	private void SW() {
		String Byte = Long.toBinaryString(registerFile.getRegister(d));
		String[] Bytes = new String[4];
		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}

		Byte = ss + Byte;
		Bytes[3] = Byte.substring(32, 40);
		Bytes[2] = Byte.substring(40, 48);
		Bytes[1] = Byte.substring(48, 56);
		Bytes[0] = Byte.substring(56, 64);
		for (int i = 0; i < 4; i++) {
			char Data = (char) Integer.parseInt(Bytes[i], 2);
			int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a) + i);
			Mem.addData(Address, Data);
		}

	}

	private void SD() {
		String Byte = Long.toBinaryString(registerFile.getRegister(d));
		String[] Bytes = new String[8];
		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}

		Byte = ss + Byte;
		Bytes[7] = Byte.substring(0, 8);
		Bytes[6] = Byte.substring(8, 16);
		Bytes[5] = Byte.substring(16, 24);
		Bytes[4] = Byte.substring(24, 32);
		Bytes[3] = Byte.substring(32, 40);
		Bytes[2] = Byte.substring(40, 48);
		Bytes[1] = Byte.substring(48, 56);
		Bytes[0] = Byte.substring(56, 64);

		for (int i = 0; i < 7; i++) {
			char Data = (char) Integer.parseInt(Bytes[i], 2);
			int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a) + i);
			Mem.addData(Address, Data);
		}

	}

	private void LB() {

		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = Integer.toBinaryString((int) Mem.getData(Address));

		for (int i = 0; i < 8 - Byte.length(); i++) {
			Byte = "0" + Byte;
		}

		String ss = "";
		String Z = Byte.substring(0, 1);
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = d + Byte;
		if(Byte.charAt(0)=='0')
		registerFile.setRegister(b, Long.parseLong(Byte.substring(1), 2));
		else
		{
			registerFile.setRegister(b,Long.parseLong(Byte.substring(1), 2)+Long.MIN_VALUE);
		}

	}

	private void LH() {
		String[] Bytes = new String[2];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
		}

		Byte = Bytes[1] + Bytes[0];

		String ss = "";
		String Z = Byte.substring(0, 1);
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = ss + Byte;
		if(Byte.charAt(0)=='0')
		registerFile.setRegister(b, Long.parseLong(Byte.substring(1), 2));
		else
		{
			registerFile.setRegister(b,Long.parseLong(Byte.substring(1), 2)+Long.MIN_VALUE);
		}

	}

	private void LW() {
		String[] Bytes = new String[4];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[2] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[3] = Integer.toBinaryString((int) Mem.getData(Address + 3));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
		}

		Byte = Bytes[3] + Bytes[2] + Bytes[1] + Bytes[0];

		String ss = "";
		String Z = Byte.substring(0, 1);
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = ss + Byte;
		if(Byte.charAt(0)=='0')
		registerFile.setRegister(b, Long.parseLong(Byte.substring(1), 2));
		else
		{
			registerFile.setRegister(b,Long.parseLong(Byte.substring(1), 2)+Long.MIN_VALUE);
		}
	}

	private void LD() {
		String[] Bytes = new String[2];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[2] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[3] = Integer.toBinaryString((int) Mem.getData(Address + 3));
		Bytes[4] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[5] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[6] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[7] = Integer.toBinaryString((int) Mem.getData(Address + 3));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
			Byte = Bytes[j] + Byte;
		}

		if(Byte.charAt(0)=='0')
		registerFile.setRegister(b, Long.parseLong(Byte.substring(1), 2));
		else
		{
			registerFile.setRegister(b,Long.parseLong(Byte.substring(1), 2)+Long.MIN_VALUE);
		}

	}

	private void LBU() {

		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = Integer.toBinaryString((int) Mem.getData(Address));

		for (int i = 0; i < 8 - Byte.length(); i++) {
			Byte = "0" + Byte;
		}

		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = d + Byte;
		registerFile.setRegister(d, Integer.parseInt(Byte, 2));

	}

	private void LHU() {
		String[] Bytes = new String[2];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
		}

		Byte = Bytes[1] + Bytes[0];

		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = ss + Byte;
		registerFile.setRegister(d, Integer.parseInt(Byte, 2));

	}

	private void LWU() {
		String[] Bytes = new String[4];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[2] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[3] = Integer.toBinaryString((int) Mem.getData(Address + 3));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
		}

		Byte = Bytes[3] + Bytes[2] + Bytes[1] + Bytes[0];

		String ss = "";
		String Z = "0";
		for (int i = 0; i < 64 - Byte.length(); i++) {
			ss = ss + Z;
		}
		Byte = ss + Byte;
		registerFile.setRegister(d, Integer.parseInt(Byte, 2));
	}

	private void LDU() {
		String[] Bytes = new String[2];
		int Address = (int)((registerFile.getRegister(b) << x) + registerFile.getRegister(a));

		String Byte = "";
		Bytes[0] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[1] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[2] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[3] = Integer.toBinaryString((int) Mem.getData(Address + 3));
		Bytes[4] = Integer.toBinaryString((int) Mem.getData(Address));
		Bytes[5] = Integer.toBinaryString((int) Mem.getData(Address + 1));
		Bytes[6] = Integer.toBinaryString((int) Mem.getData(Address + 2));
		Bytes[7] = Integer.toBinaryString((int) Mem.getData(Address + 3));

		for (int j = 0; j < Bytes.length; j++) {
			for (int i = 0; i < 8 - Bytes[j].length(); i++) {
				Bytes[j] = "0" + Bytes[j];
			}
			Byte = Bytes[j] + Byte;
		}

		registerFile.setRegister(d, Integer.parseInt(Byte, 2));

	}

}
