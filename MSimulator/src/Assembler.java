
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

	public static void InstructionFetch(String instructions) {
		instArray = new ArrayList<String>();
		instructions = instructions.replace("[", "");
		instructions = instructions.replace("]", "");
		instructions = instructions.replace("=", " ");
		instructions = instructions.replace(",", " ");

		StringTokenizer st = new StringTokenizer(instructions, " ");

		while (st.hasMoreTokens()) {
			instArray.add(st.nextToken());
		}

	}

	public static void fetchAssemblyInstruction(ProgramCounter pc, ArrayList<String> In, JTable textSegmentTable, String baseTextAddress, JEditorPane iOEditorPane) {
		
		
		DefaultTableModel model = (DefaultTableModel) textSegmentTable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		
		
		int BaseTemp = Integer.parseInt(baseTextAddress, 16);
		
		
		for (int i = 0; i < In.size(); i++) {
			
			
				InstructionFetch(In.get(i));
				String Inst = getMachineCode(pc,i);
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
					System.out.println("There is an error in Instruction #"+i);
				}
				iOEditorPane.setText(iOEditorPane.getText().equals("")?"Assemble: operation completed successfully.":"\r\nAssemble: operation completed successfully.");

			
			BaseTemp = BaseTemp + 32;
		}

	}

	public static String getMachineCode(ProgramCounter pc, int instructionInd) {
		String Is = "";
		if (instructionFormat.get(instArray.get(0).toUpperCase()).equals('R') || instructionFormat.get(instArray.get(0).toUpperCase()).equals('T') || instructionFormat.get(instArray.get(0).toUpperCase()).equals('S')
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
		}
		return Is;

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

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary("0"));
			b = ExtRegister_5(IntToBinary(register(instArray.get(1))));
			if (IntToBinary(instArray.get(2)).length() > 12) {

				Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
			} else {
				Imm = ExtRegister_12(IntToBinary(instArray.get(2)));
			}
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary(register(instArray.get(2))));
			b = ExtRegister_5(IntToBinary(register(instArray.get(3))));
			c = ExtRegister_5(IntToBinary("0"));
			d = ExtRegister_5(IntToBinary(register(instArray.get(1))));

			f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));

			x = ExtRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));

			return OP + a + b + f + x + c + d;

		} else if (instArray.size() == 5) {
			if (instructionCommand.get(instArray.get(0).toUpperCase()) == 27) {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = ExtRegister_5(IntToBinary(register(instArray.get(1))));
				b = ExtRegister_5(IntToBinary(register(instArray.get(2))));
				c = ExtRegister_5(IntToBinary(register(instArray.get(4))));
				d = ExtRegister_5(IntToBinary("0"));

				f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));

				x = ExtRegister_3(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));
				Imm = ExtRegister_2(IntToBinary(instArray.get(3)));
				Imm2 = "";

				return OP + a + b + f + Imm + c + d;
			} else {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = ExtRegister_5(IntToBinary(register(instArray.get(2))));
				b = ExtRegister_5(IntToBinary(register(instArray.get(3))));
				c = ExtRegister_5(IntToBinary("0"));
				d = ExtRegister_5(IntToBinary(register(instArray.get(1))));
				;

				if (instructionCommand.get(instArray.get(0).toUpperCase()) == 41) {

					f = ExtRegister_4(IntToBinary(register(instArray.get(4))));

					Imm = ExtRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0).toUpperCase())));

				} else {
					f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
					Imm = ExtRegister_2(IntToBinary(instArray.get(4)));
				}

				return OP + a + b + f + Imm + c + d;
			}

		} else if (instArray.size() == 6) {

		}

		return null;
	}

	private static String parseIType(ProgramCounter pc) {

		if (instArray.size() == 2) {
			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5("0");
			b = ExtRegister_5("0");
			f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			if (instArray.get(1).contains("@")) {
				Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(1)));
				if(Imm.length()>12)
					Imm=Imm.substring(Imm.length()-12);
				
			}else if (IntToBinary(instArray.get(1)).length() > 12) {
				Imm = IntToBinary(instArray.get(1)).substring(IntToBinary(instArray.get(1)).length() - 12);
			} else {
				Imm = ExtRegister_12(IntToBinary(instArray.get(1)));
			}
			return OP + a + b + f + Imm;
			
		} else if (instArray.size() == 3) {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary("0"));
			b = ExtRegister_5(IntToBinary(register(instArray.get(1))));
			if (instArray.get(2).contains("@")) {
				Imm=Integer.toBinaryString(pc.getLableAddress(instArray.get(2)));
				if(Imm.length()>12)
					Imm=Imm.substring(Imm.length()-12);
				Imm=ExtRegister_12(Imm);
			}else
			if (IntToBinary(instArray.get(2)).length() > 12) {
				Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
			} else {
				Imm = ExtRegister_12(IntToBinary(instArray.get(2)));
			}

			f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";
			if (instructionCommand.get(instArray.get(0).toUpperCase().toUpperCase()) == 25) {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = ExtRegister_5(IntToBinary(register(instArray.get(1))));
				b = ExtRegister_5(IntToBinary(register(instArray.get(3))));
				if (IntToBinary(instArray.get(2)).length() > 12) {

					Imm = IntToBinary(instArray.get(2)).substring(IntToBinary(instArray.get(2)).length() - 12);
				} else {
					Imm = ExtRegister_12(IntToBinary(instArray.get(2)));
				}

				f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
				return OP + a + b + f + Imm;
			} else {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
				a = ExtRegister_5(IntToBinary(register(instArray.get(2))));
				b = ExtRegister_5(IntToBinary(register(instArray.get(1))));
				if (IntToBinary(instArray.get(3)).length() > 12) {

					Imm = IntToBinary(instArray.get(3)).substring(IntToBinary(instArray.get(3)).length() - 12);
				} else {
					Imm = ExtRegister_12(IntToBinary(instArray.get(3)));
				}

				f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
				return OP + a + b + f + Imm;

			}

		} else if (instArray.size() == 5) {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary(register(instArray.get(2))));
			b = ExtRegister_5(IntToBinary(register(instArray.get(1))));
			Imm2 = ExtRegister_5(IntToBinary(instArray.get(4)));
			if (IntToBinary(instArray.get(3)).length() > 12) {

				Imm = IntToBinary(instArray.get(3)).substring(IntToBinary(instArray.get(3)).length() - 12);
			} else {
				Imm = ExtRegister_12(IntToBinary(instArray.get(3)));
			}
			f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0).toUpperCase())));
			return OP + a + b + f + Imm + Imm2;

		} else if (instArray.size() == 6) {

		}

		return null;
	}

	private static String parseBType(ProgramCounter pc, int instructionInd) {
		if (instructionCommand.get(instArray.get(0).toUpperCase()) > 7
				&& instructionCommand.get(instArray.get(0).toUpperCase()) < 14) {
			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary(register(instArray.get(1))));

			Imm2 = ExtRegister_5(IntToBinary(instArray.get(2)));

			Imm = instArray.get(3);
			if (Imm.contains("@")) {

				String S =Integer.toBinaryString(pc.getLableAddress(Imm) - instructionInd);
				if(S.length()>16)
				Imm = S.substring(S.length()-16);
				else
					Imm=ExtRegister_16(S);
			} else {
				Imm = ExtRegister_16(IntToBinary(instArray.get(3)));
			}
			return OP + a + Imm2 + Imm;

		} else {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));
			a = ExtRegister_5(IntToBinary(register(instArray.get(1))));

			if (instArray.size() == 2)
				b = ExtRegister_5(IntToBinary("0"));
			else
				b = ExtRegister_5(IntToBinary(register(instArray.get(2))));

			Imm = instArray.get(3);
			if (Imm.contains("@")) {
				String S =Integer.toBinaryString( pc.getLableAddress(Imm)-instructionInd);

				if(S.length()>16)
				Imm = S.substring(S.length()-16);
				else
					Imm=ExtRegister_16(S);		
				} else {
				Imm = ExtRegister_16(IntToBinary(instArray.get(3)));
			}

			return OP + a + b + Imm;
		}
	}

	private static String parseJType(ProgramCounter pc) {

		OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0).toUpperCase())));

		Imm = instArray.get(1);
		if (Imm.contains("@")) {
			Imm = ExtRegister_26(Integer.toBinaryString(pc.getLableAddress(Imm)));
		} else {
			Imm = ExtRegister_26(IntToBinary(instArray.get(1)));
		}

		return OP + Imm;

	}

	private static String register(String S) {
		
		
		String s = S.trim();
		int registerNumber = Integer.parseInt(s.substring(1));
		if (S.substring(0).toLowerCase().contains("r") && registerNumber>=0 && registerNumber< 32) {
			System.out.println(s.substring(1));
			return s.substring(1);
		} else if (s.toLowerCase().contains("t") && registerNumber>=0 && registerNumber< 10) {
			System.out.println(s.substring(1));
			return "1" + s.substring(1);
		} else if (s.toLowerCase().contains("s")&& registerNumber>=0 && registerNumber< 10) {
			System.out.println(s.substring(1));
			return "2" + s.substring(1);
		} else {
			System.out.println(s.substring(1));
			return null;
		}

	}

	private static String IntToBinary(String S) {

		return Integer.toBinaryString(Integer.parseInt(S));

	}

	public static String ExtRegister_3(String operand) {
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

	public static String ExtRegister_4(String operand) {
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

	public static String ExtRegister_5(String operand) {
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

	public static String ExtRegister_6(String operand) {
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

	public static String ExtRegister_12(String operand) {
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

	public static String ExtRegister_16(String operand) {
		String d = "";
		String Z = "0";
		for (int i = 0; i < 16 - operand.length(); i++) {
			d = d + Z;
		}

		return d + operand;
	}

	public static String ExtRegister_26(String operand) {
		String d = "";
		String Z = "0";
		for (int i = 0; i < 26 - operand.length(); i++) {
			d = d + Z;
		}

		return d + operand;
	}

	public static String ExtRegister_2(String operand) {
		String d = "";
		if (operand.length() == 1) {
			d = "0" + operand;
		} else {
			d = operand;
		}
		return d;
	}

}