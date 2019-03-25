
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Assembler {

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
	static ArrayList<String> instArray = new ArrayList();
	static boolean NotStore = true;

	public static void InstructionFetch(String instructions) {

		instructions = instructions.replace("[", "");
		instructions = instructions.replace("]", "");
		instructions = instructions.replace("=", " ");
		instructions = instructions.replace(",", " ");

		StringTokenizer st = new StringTokenizer(instructions, " ");
		int k = 0;
		while (st.hasMoreTokens()) {
			instArray.add(st.nextToken());
		}

	}

	public static String GetInstructionInBinary() {
		String Is = "";
		if (instructionFormat.get(instArray.get(0)).equals('R')
				|| instructionFormat.get(instArray.get(0)).equals('I')) {
			/*
			 * here we check if it R type or I type
			 *
			 * 
			 * 
			 */

			if (!instArray.get(2).contains("R") || !instArray.get(3).contains("R")
					|| instructionFormat.get(instArray.get(0)).equals('I')) {
				// I type

				if (!instArray.get(2).contains("RET")) {
					instArray.set(0, instArray.get(0) + "I");
				}

				return ConvertThe_I_Type();

			}

			else {

				// R type
				Is = "R type : " + instructionFormat.get(instArray.get(0));

			}

		} else if (instructionFormat.get(instArray.get(0)).equals('B')) {

			if (!instArray.get(2).contains("R") || !instArray.get(3).contains("R")
					|| instructionFormat.get(instArray.get(0)).equals('I')) {
				// I type

				instArray.set(0, instArray.get(0) + "I");

				return ConvertThe_B_Type();

			} else {

				return ConvertThe_B_Type();

			}

		} else if (instructionFormat.get(instArray.get(0)).equals('J')) {

			return ConvertThe_J_Type();

		}

		return Is;

	}

	private void initializeCommands() throws FileNotFoundException {
		instructionFormat = new HashMap<String, Character>();
		instructionCommand = new HashMap<String, Integer>();
		instructionFunction = new HashMap<String, Integer>();
		instructionX = new HashMap<String, Integer>();

		Scanner sc = new Scanner(new FileReader("resource/instructionSet.csv"));
		sc.nextLine(); // consume header
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

	private static String ConvertThe_R_Type() {

		if (instArray.size() == 2) {

		} else if (instArray.size() == 3) {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary("0")) + " ";
			b = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
			Imm = ExtRegister_12(IntToBinary(instArray.get(2))) + " ";
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
			b = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";
			c = ExtRegister_5(IntToBinary("0")) + " ";
			d = ExtRegister_5(IntToBinary(Register(instArray.get(4)))) + " ";

			f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";

			x = ExtRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0)))) + " ";

			return OP + a + b + f + x + c + d;

		} else if (instArray.size() == 5) {
			if (instructionCommand.get(instArray.get(0)) == 20) {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
				a = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
				b = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";
				c = ExtRegister_5(IntToBinary(Register(instArray.get(4)))) + " ";
				d = ExtRegister_5(IntToBinary("0")) + " ";

				f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";

				x = ExtRegister_3(Integer.toBinaryString(instructionX.get(instArray.get(0)))) + " ";
				Imm = ExtRegister_2(IntToBinary(instArray.get(4))) + " ";
				Imm2 = "";

				return OP + a + b + f + Imm + c + d;
			} else {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
				a = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";
				b = ExtRegister_5(IntToBinary(Register(instArray.get(3)))) + " ";
				c = ExtRegister_5(IntToBinary("0")) + " ";
				d = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
				;

				if (instructionCommand.get(instArray.get(0)) == 41) {

					f = ExtRegister_4(IntToBinary(Register(instArray.get(4)))) + " ";

					Imm = ExtRegister_2(Integer.toBinaryString(instructionX.get(instArray.get(0)))) + " ";

				} else {
					f = ExtRegister_4(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
					Imm = ExtRegister_2(IntToBinary(instArray.get(4))) + " ";
				}

				return OP + a + b + f + Imm + c + d;
			}

		} else if (instArray.size() == 6) {

		}

		return null;
	}

	private static String ConvertThe_I_Type() {

		if (instArray.size() == 2) {

		} else if (instArray.size() == 3) {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary("0")) + " ";
			b = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
			Imm = ExtRegister_12(IntToBinary(instArray.get(2))) + " ";
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
			return OP + a + b + f + Imm;

		} else if (instArray.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";
			if (instructionCommand.get(instArray.get(0)) == 25) {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
				a = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
				b = ExtRegister_5(IntToBinary(Register(instArray.get(3)))) + " ";
				Imm = ExtRegister_12(IntToBinary(instArray.get(2))) + " ";
				f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
				return OP + a + b + f + Imm;
			} else {

				OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
				a = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";
				b = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
				Imm = ExtRegister_12(IntToBinary(instArray.get(3))) + " ";
				f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
				return OP + a + b + f + Imm;

			}

		} else if (instArray.size() == 5) {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";
			b = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";
			Imm2 = ExtRegister_5(IntToBinary(instArray.get(4))) + " ";
			Imm = ExtRegister_12(IntToBinary(instArray.get(3))) + " ";
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
			return OP + a + b + f + Imm + Imm2;

		} else if (instArray.size() == 6) {

		}

		for (int i = 0; i < instArray.size(); i++) {
			System.out.println(instArray.get(i));
		}
		return null;
	}

	private static String ConvertThe_B_Type() {
		if (instructionCommand.get(instArray.get(0)) > 7 && instructionCommand.get(instArray.get(0)) < 14) {
			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";

			Imm2 = ExtRegister_5(IntToBinary(instArray.get(2))) + " ";
			Imm = ExtRegister_16(IntToBinary(instArray.get(3))) + " ";
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
			return OP + a + Imm2 + Imm;

		} else {

			OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";
			a = ExtRegister_5(IntToBinary(Register(instArray.get(1)))) + " ";

			if (instArray.size() == 2)
				b = ExtRegister_5(IntToBinary("0")) + " ";
			else
				b = ExtRegister_5(IntToBinary(Register(instArray.get(2)))) + " ";

			Imm = ExtRegister_16(IntToBinary(instArray.get(3))) + " ";
			f = ExtRegister_3(Integer.toBinaryString(instructionFunction.get(instArray.get(0)))) + " ";
			return OP + a + b + f + Imm;
		}
	}

	private static String ConvertThe_J_Type() {

		OP = ExtRegister_6(Integer.toBinaryString(instructionCommand.get(instArray.get(0)))) + " ";

		Imm = ExtRegister_26(IntToBinary(instArray.get(1))) + " ";

		return OP + Imm;

	}

	private static String Register(String S) {
		return S.substring(1);
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
		String Z = "0";
		for (int i = 0; i < 26 - operand.length(); i++) {
			d = d + Z;
		}

		return d + operand;
	}

}