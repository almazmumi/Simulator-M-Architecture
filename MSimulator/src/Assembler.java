
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Assembler {

	// TODO - Make an error exception
	static String OP = "", a = "", b = "", c = "", d = "", f = "", x = "", Imm = "", Imm2 = "";
	static String Rd = "", Rs = "", Rt = "", Func = "";
	static String Instruction_inBinary = "";
	static int R1, R2, R3, ImmInt, ImmInt2;

	static HashMap<String, Character> instructionFormat;
	static HashMap<String, Integer> instructionCommand;
	static HashMap<String, Integer> instructionFunction;
	static HashMap<String, Integer> instructionX;

	Assembler() throws FileNotFoundException {
		initializeCommands();
	}

	static String ff, aa, bb, dd, ImmImm;// AI=all_instructions OI=one_instruction
	static ArrayList<String> instArray;
	static boolean NotStore = true;
	static ArrayList<String> instructions = new ArrayList<String>();


	public static void fitchAssemblyInstructions(ProgramCounter pc, ArrayList<String> In, JTable textSegmentTable, String baseTextAddress, JEditorPane iOEditorPane) {
		
		DefaultTableModel model = (DefaultTableModel) textSegmentTable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		
		
		int BaseTemp = Integer.parseInt(baseTextAddress, 16);
		int i = 0;
		try {
			for (i = 0; i < In.size(); i++) {
				
				
					String instruction = In.get(i);
					instArray = new ArrayList<String>();
					instruction = instruction.replace("[", "");
					instruction = instruction.replace("]", "");
					instruction = instruction.replace("=", " ");
					instruction = instruction.replace(",", " ");
	
					StringTokenizer st = new StringTokenizer(instruction, " ");
	
					while (st.hasMoreTokens()) {
						instArray.add(st.nextToken());
					}
					
					String Inst = decodeAnInstruction(pc,i);
					int decimal = Integer.parseUnsignedInt(Inst, 2);
					String hexStr = Integer.toUnsignedString(decimal, 16);
					Instruction Instruction_In;
					if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('I')) {
						Instruction_In = new IInstruction(i, In.get(i), Inst);
						model.addRow(new Object[] { "0x" + Integer.toString(BaseTemp, 16), "0x" + hexStr.toUpperCase(), In.get(i) });
						pc.getInstructionsList().add(Instruction_In);
					} else if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('J')) {
						Instruction_In = new JInstruction(i, In.get(i), Inst);
						model.addRow(new Object[] { "0x" + Integer.toString(BaseTemp, 16), "0x" + hexStr.toUpperCase(), In.get(i) });
						pc.getInstructionsList().add(Instruction_In);
					} else if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('R')) {
						Instruction_In = new RInstruction(i, In.get(i), Inst);
						model.addRow(new Object[] { "0x" + Integer.toString(BaseTemp, 16), "0x" + hexStr.toUpperCase(), In.get(i) });
						pc.getInstructionsList().add(Instruction_In);
					} else if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('B')) {
						Instruction_In = new BInstruction(i, In.get(i), Inst);
						model.addRow(new Object[] { "0x" + Integer.toString(BaseTemp, 16), "0x" + hexStr.toUpperCase(), In.get(i) });
						pc.getInstructionsList().add(Instruction_In);
					}else {
						iOEditorPane.setText("There is an error in Instruction #"+i+"\r\n");
					}
					iOEditorPane.setText("Assemble: operation completed successfully.\r\n");
	
				
				BaseTemp = BaseTemp + 32;
			}
		}catch(Exception e){
			iOEditorPane.setText("There is an error at line "+ (i+1) + "\r\n");
		}

	}

	public static String decodeAnInstruction(ProgramCounter pc, int instructionInd) {

		if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('R')
				|| instructionFormat.get(instArray.get(0).toUpperCase()).equals('I')) {

			if(instructionCommand.get(instArray.get(0).toUpperCase()) == 60) {
				return parseIType(pc);
			}
			// here we check if it's R type or I type
			else if ((!instArray.get(2).toUpperCase().matches(".*(R|S|T).*") || !instArray.get(3).toUpperCase().matches(".*(R|S|T).*")
					|| instructionFormat.get(instArray.get(0).toUpperCase()).equals('I'))
					&& !(instArray.get(1).toUpperCase().matches(".*(R|S|T).*") && instArray.get(2).toUpperCase().matches(".*(R|S|T).*")
							&& !instArray.get(3).toUpperCase().matches(".*(R|S|T).*") && instArray.size() == 5)) {
				// I type
				if (!instArray.get(2).toUpperCase().contains("RET")) {
					instArray.set(0, instArray.get(0).toUpperCase() + "I");
				}
				return parseIType(pc);
			}

			else {
				// R type
				return parseRType();

			}

		} else if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('B')) {

			if ((!instArray.get(1).toUpperCase().matches(".*(R|S|T).*") || !instArray.get(2).toUpperCase().matches(".*(R|S|T).*")
					|| instructionFormat.get(instArray.get(0).toUpperCase()).equals('I'))
					&& !instArray.get(0).toUpperCase().contains("LOOP")) {
				// I type
				instArray.set(0, instArray.get(0).toUpperCase() + "I");
				return parseBType(pc,instructionInd);
			} else {
				return parseBType(pc,instructionInd);
			}

		} else if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('J')) {
			return parseJType(pc);
		}else 
		return null;

	}

	public static void initializeCommands() throws FileNotFoundException {
		instructionFormat = new HashMap<String, Character>();
		instructionCommand = new HashMap<String, Integer>();
		instructionFunction = new HashMap<String, Integer>();
		instructionX = new HashMap<String, Integer>();

		Scanner sc = new Scanner(new FileReader("resources/instructionSet.csv"));
		sc.nextLine();
		while (sc.hasNextLine()) {
		
			String[] instruction = sc.nextLine().split(",");
			instructionFormat.put(instruction[0], instruction[1].charAt(0));
			int command = Integer.parseInt(instruction[2]);
			instructionCommand.put(instruction[0], command);
			instructionFunction.put(instruction[0], Integer.parseInt(instruction[3]));
			instructionX.put(instruction[0], Integer.parseInt(instruction[4]));
			
		}
		sc.close();
	}

	private static String parseRType() {

		if (instArray.size() == 2) {

		} else if (instArray.size() == 3) {

			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary("0"));
			b = extRegister_5(IntToBinary(Register(instArray.get(1))));
			if (IntToBinary(instArray.get(2)).length() > 12) {

				Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
			} else {
				Imm = extRegister_12(IntToBinary(instArray.get(2)));
			}
			f = extRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";

			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary(Register(instArray.get(2))));
			b = extRegister_5(IntToBinary(Register(instArray.get(3))));
			c = extRegister_5(IntToBinary("0"));
			d = extRegister_5(IntToBinary(Register(instArray.get(1))));

			f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));

			x = extRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));

			return OP + a + b + f + x + c + d;

		} else if (instArray.size() == 5) {
			if (instructionCommand.get(instArray.get(0).toUpperCase()) == 27) {

				OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = extRegister_5(IntToBinary(Register(instArray.get(1))));
				b = extRegister_5(IntToBinary(Register(instArray.get(2))));
				c = extRegister_5(IntToBinary(Register(instArray.get(4))));
				d = extRegister_5(IntToBinary("0"));

				f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));

				x = extRegister_3(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));
				Imm = extRegister_2(IntToBinary(instArray.get(3)));
				Imm2 = "";

				return OP + a + b + f + Imm + c + d;
			} else {

				OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = extRegister_5(IntToBinary(Register(instArray.get(2))));
				b = extRegister_5(IntToBinary(Register(instArray.get(3))));
				c = extRegister_5(IntToBinary("0"));
				d = extRegister_5(IntToBinary(Register(instArray.get(1))));
				;

				if (instructionCommand.get(instArray.get(0).toUpperCase()) == 41) {

					f = extRegister_4(IntToBinary(Register(instArray.get(4))));

					Imm = extRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));

				} else {
					f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
					Imm = extRegister_2(IntToBinary(instArray.get(4)));
				}

				return OP + a + b + f + Imm + c + d;
			}

		} else if (instArray.size() == 6) {

		}

		return null;
	}

	private static String parseIType(ProgramCounter pc) {
		System.out.println(instArray.get(0));
		if (instArray.size() == 2) {
			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5("0");
			b = extRegister_5("0");
			f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			if (instArray.get(1).contains("@")) {
				Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(1)));
				if(Imm.length()>12)
					Imm=Imm.substring(Imm.length()-12);
				
			}else if (IntToBinary(instArray.get(1)).length() > 12) {
				Imm = IntToBinary(instArray.get(1)).substring(IntToBinary(instArray.get(1)).length() - 12);
			} else {
				Imm = extRegister_12(IntToBinary(instArray.get(1)));
			}
			return OP + a + b + f + Imm;
			
		} else if (instArray.size() == 3) {

			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary("0"));
			b = extRegister_5(IntToBinary(Register(instArray.get(1))));
			if (instArray.get(2).contains("@")) {
				System.out.print("SS "+pc.getLableAddress(instArray.get(2)));
				Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(2)));
				if(Imm.length()>12)
					Imm=Imm.substring(Imm.length()-12);
				Imm=extRegister_12(Imm);
			}else
			if (IntToBinary(instArray.get(2)).length() > 12) {
				
				Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
			} else {
				Imm = extRegister_12(IntToBinary(instArray.get(2)));
			}

			f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {
			if (instructionCommand.get(instArray.get(0).toUpperCase().toUpperCase()) == 25) {

				OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = extRegister_5(IntToBinary(Register(instArray.get(1))));
				b = extRegister_5(IntToBinary(Register(instArray.get(3))));
				if (instArray.get(2).contains("@")) {
					Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(2)));
					if(Imm.length()>12)
						Imm=Imm.substring(Imm.length()-12);
					Imm=extRegister_12(Imm);
				}else
				if (IntToBinary(instArray.get(2)).length() > 12) {

					Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
				} else {
					Imm = extRegister_12(IntToBinary(instArray.get(2)));
				}

				f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
				return OP + a + b + f + Imm;
			} else {

				OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = extRegister_5(IntToBinary(Register(instArray.get(2))));
				b = extRegister_5(IntToBinary(Register(instArray.get(1))));
				if (instArray.get(3).contains("@")) {
					Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(3)));
					if(Imm.length()>12)
						Imm=Imm.substring(Imm.length()-12);
					Imm=extRegister_12(Imm);
				}else
				if (IntToBinary(instArray.get(3)).length() > 12) {

					Imm = IntToBinary(instArray.get(3)).substring(IntToBinary(instArray.get(3)).length() - 12);
				} else {
					Imm = extRegister_12(IntToBinary(instArray.get(3)));
				}

				f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
				return OP + a + b + f + Imm;

			}

		} else if (instArray.size() == 5) {

			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary(Register(instArray.get(2))));
			b = extRegister_5(IntToBinary(Register(instArray.get(1))));
			Imm2 = extRegister_5(IntToBinary(instArray.get(4)));
			if (instArray.get(3).contains("@")) {
				Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(3)));
				if(Imm.length()>12)
					Imm=Imm.substring(Imm.length()-12);
				Imm=extRegister_12(Imm);
			}else
			if (IntToBinary(instArray.get(3)).length() > 12) {

				Imm = IntToBinary(instArray.get(3)).substring(IntToBinary(instArray.get(3)).length() - 12);
			} else {
				Imm = extRegister_12(IntToBinary(instArray.get(3)));
			}
			f = extRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm + Imm2;

		} else if (instArray.size() == 6) {

		}

		return null;
	}

	private static String parseBType(ProgramCounter pc, int instructionInd) {
		if (instructionCommand.get(instArray.get(0).toUpperCase()) > 7
				&& instructionCommand.get(instArray.get(0).toUpperCase()) < 14) {
			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary(Register(instArray.get(1))));

			Imm2 = extRegister_5(IntToBinary(instArray.get(2)));

			Imm = instArray.get(3);
			if (Imm.contains("@")) {
				String S =Integer.toBinaryString(pc.getLableAddress(Imm) - instructionInd);
				
				if(S.length()>16)
				Imm = S.substring(S.length()-16);
				else
					Imm=extRegister_16(S);
			} else {
				Imm = extRegister_16(IntToBinary(instArray.get(3)));
			}
			return OP + a + Imm2 + Imm;

		} else {

			OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = extRegister_5(IntToBinary(Register(instArray.get(1))));

			if (instArray.size() == 2)
				b = extRegister_5(IntToBinary("0"));
			else
				b = extRegister_5(IntToBinary(Register(instArray.get(2))));

			Imm = instArray.get(3);
			if (Imm.contains("@")) {
				String S =Integer.toBinaryString( pc.getLableAddress(Imm)-instructionInd);
				
				if(S.length()>16)
				Imm = S.substring(S.length()-16);
				else
					Imm=extRegister_16(S);		
				} else {
				Imm = extRegister_16(IntToBinary(instArray.get(3)));
			}

			return OP + a + b + Imm;
		}
	}

	private static String parseJType(ProgramCounter pc) {

		OP = extRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));

		Imm = instArray.get(1);
		if (Imm.contains("@")) {
			Imm = extRegister_26(Integer.toBinaryString(pc.getLableAddress(Imm)));
		} else {
			Imm = extRegister_26(IntToBinary(instArray.get(1)));
		}

		return OP + Imm;

	}

	private static String Register(String S) {
		int registerNumber = Integer.parseInt(S.substring(1));
		if (S.substring(0).toLowerCase().contains("r") && registerNumber>=0 && registerNumber< 32) {
			return S.substring(1);
		} else if (S.substring(0).toLowerCase().contains("t")&& registerNumber>=0 && registerNumber< 10) {
			
			return "1" + S.substring(1);
		} else if (S.substring(0).toLowerCase().contains("s")&& registerNumber>=0 && registerNumber< 10) {
			return "2" + S.substring(1);
		} else {
			return null;
		}

	}

	private static String IntToBinary(String S) {

		return Integer.toBinaryString(Integer.parseInt(S));

	}

	public static String extRegister_3(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "00" + operand;
		} else if (operand.length() == 2) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

	public static String extRegister_4(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "000" + operand;
		} else if (operand.length() == 2) {
			d = "00" + operand;
		} else if (operand.length() == 3) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

	public static String extRegister_5(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "0000" + operand;
		} else if (operand.length() == 2) {
			d = "000" + operand;
		} else if (operand.length() == 3) {
			d = "00" + operand;
		} else if (operand.length() == 4) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

	public static String extRegister_6(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "00000" + operand;
		} else if (operand.length() == 2) {
			d = "0000" + operand;
		} else if (operand.length() == 3) {
			d = "000" + operand;
		} else if (operand.length() == 4) {
			d = "00" + operand;
		} else if (operand.length() == 5) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

	public static String extRegister_12(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "00000000000" + operand;
		} else if (operand.length() == 2) {
			d = "0000000000" + operand;
		} else if (operand.length() == 3) {
			d = "000000000" + operand;
		} else if (operand.length() == 4) {
			d = "00000000" + operand;
		} else if (operand.length() == 5) {
			d = "0000000" + operand;
		} else if (operand.length() == 6) {
			d = "000000" + operand;
		} else if (operand.length() == 7) {
			d = "00000" + operand;
		} else if (operand.length() == 8) {
			d = "0000" + operand;
		} else if (operand.length() == 9) {
			d = "000" + operand;
		} else if (operand.length() == 10) {
			d = "00" + operand;
		} else if (operand.length() == 11) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

	public static String extRegister_16(String operand) {
		String d = "";
		String Z = "0";
		for (int i = 0; i < 16 - operand.length(); i++) {
			d = d + Z;
		}

		return d + operand;
	}

	public static String extRegister_26(String operand) {
		String d = "";
		String Z = "0";
		for (int i = 0; i < 26 - operand.length(); i++) {
			d = d + Z;
		}

		return d + operand;
	}

	public static String extRegister_2(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

}