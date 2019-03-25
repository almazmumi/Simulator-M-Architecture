import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Assembler {
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		
		
		
		loadAllInstructions();
		String instructions[] = new String[7];
		instructions[0] = "BEQ R1, R4, 2 ";
		instructions[1] = "BNE R3, R5, 2";
        instructions[2]	= "BLT R2, R4, 2 ";
        instructions[3]	= "BGE R5, R5, 2 ";
        instructions[4]	= "BLTU R6, R5, 2 ";
        instructions[5]	= "J 1 ";
//        instructions[6]	= "JAL 2";
        
		instructions[6] = "add r5 = r5, 5"; // Successes to count it as an I type
//		instructions[1] = "cor r5 = r7 , 3 ";
//        instructions[2]	= "mul r5  r9  r30 ";
//        instructions[3]	= "geu r25 = r13 , r30 ";
//        instructions[4]	= "shr r3 = r2 , r30 ";
		
		
//		String[] Load_Store = { "SW", "SH", "SW", "SD" };
//
//		String OP = "", a = "", b = "", c = "", d = "", f = "", x = "", Imm = "";
//
//		String Rd = "", Rs = "", Rt = "", Func = "";
//
//		String ff, aa, bb, dd, ImmImm;// AI=all_instructions OI=one_instruction

		//B=====================================================================================================================
		ProgramCounter pc = new ProgramCounter();

		for (int i = 0; i < instructions.length; i++) {
			
			
			
//			System.out.println("instruction # " + i);

//			boolean NotStore = true;
			
			
			
			
			instructions[i] = instructions[i].replace("[", "");
			instructions[i] = instructions[i].replace("]", "");
			instructions[i] = instructions[i].replace("=", " ");
			instructions[i] = instructions[i].replace(",", " ");

			ArrayList<String> instructionArray = new ArrayList();

			StringTokenizer st = new StringTokenizer(instructions[i], " ");
			int k = 0;
			while (st.hasMoreTokens()) {
				instructionArray.add(st.nextToken());
			}
			
			InstructionObject instructionObject = instructionFormat.get(instructionArray.get(0).toUpperCase());
			switch(instructionObject.getFormat()) {
			case "B":
				if(instructionObject.getOpcode()>=15 && instructionObject.getOpcode() <= 23) {
					String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
					String bBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(2))),5);
					String offset = padLeftZeros(toBinary((instructionArray.get(3))),16);
					String instructionBinary = opcodeBinary + aBinary + bBinary + offset;
					BInstruction BInstruction = new BInstruction(i, instructions[i], instructionBinary);
					pc.getInstructionsList().add(BInstruction);
					
				}else if(instructionObject.getOpcode() >= 8 && instructionObject.getOpcode() <= 13 ) {
					String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
					String bBinary = padLeftZeros(toBinary(instructionArray.get(2)),5);
					String offset = padLeftZeros(toBinary((instructionArray.get(3))),16);
					String instructionBinary = opcodeBinary + aBinary + bBinary + offset;
					BInstruction BInstruction = new BInstruction(i, instructions[i], instructionBinary);
					pc.getInstructionsList().add(BInstruction);
					
				}else if(instructionObject.getOpcode() >= 14){
					String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
					String bBinary = padLeftZeros("0",5);
					String offset = padLeftZeros(toBinary((instructionArray.get(3))),16);
					String instructionBinary = opcodeBinary + aBinary + bBinary + offset;
					BInstruction BInstruction = new BInstruction(i, instructions[i], instructionBinary);
					pc.getInstructionsList().add(BInstruction);
				}
				
				break;
			case "J":
				
				String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
				String offset = padLeftZeros(toBinary(instructionArray.get(1)),26);
				String instructionBinary = opcodeBinary + offset;
				JInstruction JInstruction = new JInstruction(i, instructions[i], instructionBinary);
				pc.getInstructionsList().add(JInstruction);
				
				break;
			case "R": 	// NOT DONE YET, 
						//There are two type one with C missing and one with D missing.
						
				
				if(instructionArray.get(3).toLowerCase().contains("r")){
					// Here check if it's R or I. If it is R function, it will be done and get out of this switch loop 
					// if its otherwise it will go the i type section.
//					String opcodeBinary1 = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
//					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
//					String bBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(2))),5);
//					String fBinary = padLeftZeros(toBinary(String.valueOf((instructionObject.getFunction()))),5);
//					String xBinary = padLeftZeros(toBinary(String.valueOf((instructionObject.getX()))),5);
//					String instructionBinary1 = opcodeBinary1 + aBinary + bBinary + fBinary + xBinary;
//					RInstruction RInstruction = new RInstruction(i, instructions[i], instructionBinary1);
//					pc.getInstructionsList().add(RInstruction);
					
					break;
				}
				instructionObject = instructionFormat.get(instructionArray.get(0).toUpperCase()+"I");
			case "I":
				
				String opcodeBinary1 = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
				String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
				String bBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(2))),5);
				String fBinary = padLeftZeros(toBinary((instructionArray.get(3))),4);
				String immBinary = padLeftZeros(toBinary(String.valueOf(instructionArray.get(3))),12);
				String instructionBinary1 = opcodeBinary1 + aBinary + bBinary + fBinary + immBinary;
				
				IInstruction IInstruction = new IInstruction(i, instructions[i], instructionBinary1);
				pc.getInstructionsList().add(IInstruction);
				break;
			}
			//B====================================================================================================
				
				
				
			

			
			///////////////////////////////////////////////////////////
//			for (int j = 0; j < Load_Store.length; j++) {
//				if (Load_Store[j].contains(instructionArray.get(0))) {
//					NotStore = false;
//					break;
//				}
//			}
			///////////////////////////////////////////////////////////////////////

//			if (instructionArray.get(3).contains("r") && NotStore) {// R-FORMAT
//				System.out.println("R-FORMAT");
//
//				f = getFunc(instructionArray.get(0));
//				d = getRegister(instructionArray.get(1));
//				a = getRegister(instructionArray.get(2));
//				b = getRegister(instructionArray.get(3));
//				System.out.println("Instruction Number");
//				System.out.println(f + " " + d + " " + a + " " + b);
//
//				ff = toBinary(f);
//				dd = toBinary(d);
//				aa = toBinary(a);
//				bb = toBinary(b);
//				System.out.println("Instruction in binary befor extending ");
//				System.out.println(ff + " " + dd + " " + aa + " " + bb);
//
//				f = ExtRegister_4(ff);
//				d = ExtRegister_5(dd);
//				a = ExtRegister_5(aa);
//				b = ExtRegister_5(bb);
//				System.out.println("Instruction in binary after extending ");
//				System.out.println(f + " " + d + " " + a + " " + b);
//			} else if (!instructionArray.get(3).contains("r")) {// I-FORMAT
//				if (NotStore == false) {
//
//					b = getRegister(instructionArray.get(3));// Rb
//					a = getRegister(instructionArray.get(1));// Rs
//					Imm = ExtRegister_12(instructionArray.get(2));// Imm
//
//				} else {
//
//					f = getFunc(instructionArray.get(0));// function
//					b = getRegister(instructionArray.get(1));// Rb
//					a = getRegister(instructionArray.get(2));// Rs
//					Imm = instructionArray.get(3);// Imm
//					System.out.println("Instruction Number");
//					System.out.println(f + " " + b + " " + a + " " + Imm);
//
//					ff = toBinary(f);
//					bb = toBinary(b);
//					aa = toBinary(a);
//					ImmImm = toBinary(Imm);
//					System.out.println("Instruction in binary befor extending ");
//					System.out.println(ff + " " + bb + " " + aa + " " + ImmImm);
//
//					f = ExtRegister_4(ff);
//					b = ExtRegister_5(bb);
//					a = ExtRegister_5(aa);
//					Imm = ExtRegister_12(ImmImm);
//					System.out.println("Instruction in binary after extending ");
//					System.out.println(f + " " + b + " " + a + " " + Imm);

//				}
//			}
//			System.out.println();
		}
		
		for(int j = 0; j<pc.getInstructionsList().size();j++) {
			System.out.println(pc.getInstructionsList().get(j).getInstructionName().trim() + " - " + pc.getInstructionsList().get(j).getClass().getSimpleName().substring(0,1)+ " Format" );
			System.out.println(pc.getInstructionsList().get(j).getInstructionBinary());
			System.out.println();
			
		}
	}

	
	
	
	
	





	public static String padLeftZeros(String inputString, int length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append('0');
	    }
	    sb.append(inputString);
	 
	    return sb.toString();
	}
	

	private static HashMap<String,InstructionObject> instructionFormat;
	
	public static void loadAllInstructions() throws FileNotFoundException {

		int opcode;
		String format;
		String name;
		int function;
		int x;
		instructionFormat = new HashMap<String, InstructionObject>();
		Scanner sc = new Scanner(new FileReader("resources/instructionSet.csv"));
		sc.nextLine();		//consume header
		while(sc.hasNextLine())
		{	String[] instruction = sc.nextLine().split(",");
			opcode = Integer.parseInt(instruction[2]) ;
			format = instruction[1];
			name = instruction[0];
			function = Integer.parseInt(instruction[3]);
			x = Integer.parseInt(instruction[4]);
			
			
			instructionFormat.put(instruction[0], new InstructionObject(name,format,opcode,function,x));

		}	
		sc.close();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	public static String getFunc(String Func) {
//		String f = "";
//		if (Func.equals("add") | Func.equals("add")) {
//			f = "0";
//		} else if (Func.equals("nadd") | Func.equals("shr")) {
//			f = "1";
//		} else if (Func.equals("and") | Func.equals("sar")) {
//			f = "2";
//		} else if (Func.equals("cand") | Func.equals("ror")) {
//			f = "3";
//		} else if (Func.equals("or")) {
//			f = "4";
//		} else if (Func.equals("cor")) {
//			f = "5";
//		} else if (Func.equals("xor")) {
//			f = "6";
//		} else if (Func.equals("xnor") | Func.equals("set")) {
//			f = "7";
//		} else if (Func.equals("eq") | Func.equals("mul")) {
//			f = "8";
//		} else if (Func.equals("ne")) {
//			f = "9";
//		} else if (Func.equals("lt")) {
//			f = "10";
//		} else if (Func.equals("ge")) {
//			f = "11";
//		} else if (Func.equals("ltu") | Func.equals("div")) {
//			f = "12";
//		} else if (Func.equals("geu") | Func.equals("mod")) {
//			f = "13";
//		} else if (Func.equals("min") | Func.equals("divu")) {
//			f = "14";
//		} else if (Func.equals("max") | Func.equals("modu")) {
//			f = "15";
//		} else {
//			f = "0";
//		}
//		return f;
//	}

	public static String getRegister(String operand) {
		return operand.substring(1);
	}

	private static String toBinary(String operand) {
		int dd = Integer.parseInt(operand);
		String a = Integer.toBinaryString(dd);
		return a;
	}

	
//	public static String ExtRegister_16(String operand) {
//		String d = "";
//		if (operand.length() == 1) {
//			d = "000000000000000" + operand;
//		} else if (operand.length() == 2) {
//			d = "00000000000000" + operand;
//		} else if (operand.length() == 3) {
//			d = "0000000000000" + operand;
//		} else if (operand.length() == 4) {
//			d = "000000000000" + operand;
//		} else if (operand.length() == 5) {
//			d = "00000000000" + operand;
//		} else if (operand.length() == 6) {
//			d = "0000000000" + operand;
//		} else if (operand.length() == 7) {
//			d = "000000000" + operand;
//		} else if (operand.length() == 8) {
//			d = "00000000" + operand;
//		} else if (operand.length() == 9) {
//			d = "0000000" + operand;
//		} else if (operand.length() == 10) {
//			d = "000000" + operand;
//		} else if (operand.length() == 11) {
//			d = "00000" + operand;
//		}else if (operand.length() == 12) {
//			d = "0000" + operand;
//		}else if (operand.length() == 13) {
//			d = "000" + operand;
//		}else if (operand.length() == 14) {
//			d = "00" + operand;
//		}else if (operand.length() == 15) {
//			d = "0" + operand;
//		}else {
//			d = operand;
//		}
//		return d;
//	}
//	
//	public static String ExtRegister_6(String operand) {
//		String d = "";
//		if (operand.length() == 1) {
//			d = "00000" + operand;
//		} else if (operand.length() == 2) {
//			d = "0000" + operand;
//		} else if (operand.length() == 3) {
//			d = "000" + operand;
//		} else if (operand.length() == 4) {
//			d = "00" + operand;
//		} else if (operand.length() == 5) {
//			d = "0" + operand;
//		} else {
//			d = operand;
//		}
//		return d;
//	}
//	
//	public static String ExtRegister_4(String operand) {
//		String d = "";
//		if (operand.length() == 1) {
//			d = "000" + operand;
//		} else if (operand.length() == 2) {
//			d = "00" + operand;
//		} else if (operand.length() == 3) {
//			d = "0" + operand;
//		} else {
//			d = operand;
//		}
//		return d;
//	}
//
//	public static String ExtRegister_5(String operand) {
//		String d = "";
//		if (operand.length() == 1) {
//			d = "0000" + operand;
//		} else if (operand.length() == 2) {
//			d = "000" + operand;
//		} else if (operand.length() == 3) {
//			d = "00" + operand;
//		} else if (operand.length() == 4) {
//			d = "0" + operand;
//		} else {
//			d = operand;
//		}
//		return d;
//	}
//
//	public static String ExtRegister_12(String operand) {
//		String d = "";
//		if (operand.length() == 1) {
//			d = "00000000000" + operand;
//		} else if (operand.length() == 2) {
//			d = "0000000000" + operand;
//		} else if (operand.length() == 3) {
//			d = "000000000" + operand;
//		} else if (operand.length() == 4) {
//			d = "00000000" + operand;
//		} else if (operand.length() == 5) {
//			d = "0000000" + operand;
//		} else if (operand.length() == 6) {
//			d = "000000" + operand;
//		} else if (operand.length() == 7) {
//			d = "00000" + operand;
//		} else if (operand.length() == 8) {
//			d = "0000" + operand;
//		} else if (operand.length() == 9) {
//			d = "000" + operand;
//		} else if (operand.length() == 10) {
//			d = "00" + operand;
//		} else if (operand.length() == 11) {
//			d = "0" + operand;
//		} else {
//			d = operand;
//		}
//		return d;
//	}
}
