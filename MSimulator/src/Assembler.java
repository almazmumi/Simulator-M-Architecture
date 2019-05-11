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

	static String OP = "", a = "", b = "", c = "", d = "", f = "", x = "", imm = "", Imm2 = "";
	static ArrayList<String> instructionTokens;
	private static HashMap<String, InstructionObject> instructionsListHashMap;
	private boolean status;
	
	public Assembler() throws FileNotFoundException {
		initializeCommands();
	}
	
	
	public void fetchAssemblyInstructions(ArrayList<String> instructionList) {

		
		DefaultTableModel model = (DefaultTableModel) GlobalVariables.textSegmentTable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		int baseTemp = Integer.parseInt(GlobalVariables.textBaseAddress, 16);
		int offset = 0;
		int j = 0;
		try {
			for (j = 0; j < instructionList.size(); j++) {

				
				String instruction = instructionList.get(j);
				if(instruction == null) {
					offset++;
					instructionList.remove(j);
					j--;
				}else{
					
				
					instructionTokens = new ArrayList<String>();
					instruction = instruction.replace("[", "");
					instruction = instruction.replace("]", "");
					instruction = instruction.replace("=", " ");
					instruction = instruction.replace(",", " ");
					StringTokenizer st = new StringTokenizer(instruction, " ");
	
					while (st.hasMoreTokens()) {
						instructionTokens.add(st.nextToken());
					}
	
					String binaryInstruction = decodeAnInstruction(GlobalVariables.pc, j);
				
					Instruction i;
					InstructionObject temp = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());

					if (temp.getFormat() == 'I') {
						i = new IInstruction(j, instructionList.get(j), binaryInstruction);
						GlobalVariables.pc.getInstructionsList().add(i);
					} else if (temp.getFormat() == 'J') {
						i = new JInstruction(j, instructionList.get(j), binaryInstruction);
						GlobalVariables.pc.getInstructionsList().add(i);
					} else if (temp.getFormat() == 'R') {
						i = new RInstruction(j, instructionList.get(j), binaryInstruction);
						GlobalVariables.pc.getInstructionsList().add(i);
					} else if (temp.getFormat() == 'B') {
						i = new BInstruction(j, instructionList.get(j), binaryInstruction);
						
						GlobalVariables.pc.getInstructionsList().add(i);
					}

				}
			}
			status = true;
		} catch (Exception e) {
			GlobalVariables.IOEditorPane.setText("There is an error at line " + (j + 1 + offset) + "\r\n");
			status = false;
		}
		
		if(status) {
			for (j = 0; j < instructionList.size(); j++) {
				String binaryInstruction = GlobalVariables.pc.getInstructionsList().get(j).getInstructionBinary();
				int decimalInstruction = Integer.parseUnsignedInt(binaryInstruction, 2);
				String hexInstruction = Integer.toUnsignedString(decimalInstruction, 16);
				String instructionAddress = Integer.toString(baseTemp, 16);
				
				String s = "";
				for (int k = 0; k < 8-k; k++) {
					s += "0";
				}
				
				instructionAddress = s + instructionAddress;
				
				model.addRow(new Object[] { "0x" + instructionAddress, "0x" + hexInstruction.toUpperCase(), instructionList.get(j) });
				
				GlobalVariables.IOEditorPane.setText("Assemble: operation completed successfully.\r\n");

				baseTemp = baseTemp + 16;
			}
		}
		
		
		


	}

	public String decodeAnInstruction(ProgramCounter pc, int instructionInd) {
		InstructionObject i = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());

		if (i.getFormat() == 'R' || i.getFormat() == 'I') {

			if (i.getOpcode() == 60) {
				return parseIType(pc);

			} else if ((!instructionTokens.get(2).toUpperCase().matches(".*(R|S|T).*")
					|| !instructionTokens.get(3).toUpperCase().matches(".*(R|S|T).*") || i.getFormat() == 'I')
					&& !(instructionTokens.get(1).toUpperCase().matches(".*(R|S|T).*")
					&& instructionTokens.get(2).toUpperCase().matches(".*(R|S|T).*")
					&& !instructionTokens.get(3).toUpperCase().matches(".*(R|S|T).*")
					&& instructionTokens.size() == 5)) {

				if (!instructionTokens.get(2).toUpperCase().contains("RET")) {
					instructionTokens.set(0, instructionTokens.get(0).toUpperCase() + "I");
				}
				return parseIType(pc);
			
			
			} else {
				return parseRType();
			}

		} else if (i.getFormat() == 'B') {

			if ((!instructionTokens.get(1).toUpperCase().matches(".*(R|S|T).*")
					|| !instructionTokens.get(2).toUpperCase().matches(".*(R|S|T).*")
					|| i.getFormat() == 'I')
					&& !instructionTokens.get(0).toUpperCase().contains("LOOP")) {
				// I type
				instructionTokens.set(0, instructionTokens.get(0).toUpperCase() + "I");
				return parseBType(pc, instructionInd);
			} else {
				return parseBType(pc, instructionInd);
			}

		} else if (i.getFormat() == 'J') {
			return parseJType(pc);
		} else
			return null;

	}

	public void initializeCommands() throws FileNotFoundException {
		instructionsListHashMap = new HashMap<String, InstructionObject>();
		Scanner sc = new Scanner(new FileReader("resources/instructionSet.csv"));
		sc.nextLine();
		while (sc.hasNextLine()) {

			String[] instruction = sc.nextLine().split(",");
			int opcode = Integer.parseInt(instruction[2]);
			InstructionObject i = new InstructionObject(instruction[0], instruction[1].charAt(0), opcode,
					Integer.parseInt(instruction[3]), Integer.parseInt(instruction[4]));
			instructionsListHashMap.put(instruction[0], i);

		}
		sc.close();
	}

	private String parseRType() {
		InstructionObject i = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());

		if (instructionTokens.size() == 2) {

		} else if (instructionTokens.size() == 3) {
			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary("0"));
			b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
			if (intToBinary(instructionTokens.get(2)).length() > 12) {
				imm = intToBinary(instructionTokens.get(2))
						.substring(intToBinary(instructionTokens.get(2)).length() - 12);
			} else {
				imm = zeroExtend(12, intToBinary(instructionTokens.get(2)));
			}
			f = zeroExtend(3, Integer.toBinaryString(i.getFunction()));
			return OP + a + b + f + imm;

		} else if (instructionTokens.size() == 4) {// OP="",a="",b="",c="",d="",f="",x="",Imm="",Imm2="";

			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));
			b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(3))));
			c = zeroExtend(5, intToBinary("0"));
			d = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));

			f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));

			x = zeroExtend(2, Integer.toBinaryString(i.getX()));

			return OP + a + b + f + x + c + d;

		} else if (instructionTokens.size() == 5) {
			if (i.getOpcode() == 27) {

				OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
				a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
				b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));
				c = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(4))));
				d = zeroExtend(5, intToBinary("0"));
				f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));
				x = zeroExtend(3, Integer.toBinaryString(i.getX()));
				imm = zeroExtend(2, intToBinary(instructionTokens.get(3)));
				Imm2 = "";

				return OP + a + b + f + imm + c + d;
			} else {

				OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
				a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));
				b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(3))));
				c = zeroExtend(5, intToBinary("0"));
				d = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
				;

				if (i.getOpcode() == 41) {

					f = zeroExtend(4, intToBinary(getRegisterNumber(instructionTokens.get(4))));

					imm = zeroExtend(2, 
							Integer.toBinaryString(i.getX()));

				} else {
					f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));
					imm = zeroExtend(2, intToBinary(instructionTokens.get(4)));
				}

				return OP + a + b + f + imm + c + d;
			}

		} else if (instructionTokens.size() == 6) {

		}

		return null;
	}

	private String parseIType(ProgramCounter pc) {
		InstructionObject i = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());
		if (instructionTokens.size() == 2) {
			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, "0");
			b = zeroExtend(5, "0");
			f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));
			if (instructionTokens.get(1).contains("@")) {
				imm = Integer.toBinaryString(pc.getLableAddress(instructionTokens.get(1)));
				if (imm.length() > 12)
					imm = imm.substring(imm.length() - 12);

			} else if (intToBinary(instructionTokens.get(1)).length() > 12) {
				imm = intToBinary(instructionTokens.get(1)).substring(intToBinary(instructionTokens.get(1)).length() - 12);
			} else {
				imm = zeroExtend(12, intToBinary(instructionTokens.get(1)));
			}
			return OP + a + b + f + imm;

		} else if (instructionTokens.size() == 3) {

			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary("0"));
			b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
			if (instructionTokens.get(2).contains("@")) {
				imm = Integer.toBinaryString(pc.getLableAddress(instructionTokens.get(2)));
				if (imm.length() > 12)
					imm = imm.substring(imm.length() - 12);
				imm = zeroExtend(12, imm);
			} else if (intToBinary(instructionTokens.get(2)).length() > 12) {

				imm = intToBinary(instructionTokens.get(2))
						.substring(intToBinary(instructionTokens.get(2)).length() - 12);
			} else {
				imm = zeroExtend(12, intToBinary(instructionTokens.get(2)));
			}

			f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));
			return OP + a + b + f + imm;

		} else if (instructionTokens.size() == 4) {
			if (i.getOpcode() == 25) {

				OP = zeroExtend(6, 
						Integer.toBinaryString(i.getOpcode()));
				a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
				b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(3))));
				if (instructionTokens.get(2).contains("@")) {
					imm = Integer.toBinaryString(pc.getLableAddress(instructionTokens.get(2)));
					if (imm.length() > 12)
						imm = imm.substring(imm.length() - 12);
					imm = zeroExtend(12, imm);
				} else if (intToBinary(instructionTokens.get(2)).length() > 12) {

					imm = intToBinary(instructionTokens.get(2))
							.substring(intToBinary(instructionTokens.get(2)).length() - 12);
				} else {
					imm = zeroExtend(12, intToBinary(instructionTokens.get(2)));
				}

				f = zeroExtend(4, 
						Integer.toBinaryString(i.getFunction()));
				return OP + a + b + f + imm;
			} else {

				OP = zeroExtend(6, 
						Integer.toBinaryString(i.getOpcode()));
				a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));
				b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
				if (instructionTokens.get(3).contains("@")) {
					imm = Integer.toBinaryString(pc.getLableAddress(instructionTokens.get(3)));
					if (imm.length() > 12)
						imm = imm.substring(imm.length() - 12);
					imm = zeroExtend(12, imm);
				} else if (intToBinary(instructionTokens.get(3)).length() > 12) {

					imm = intToBinary(instructionTokens.get(3))
							.substring(intToBinary(instructionTokens.get(3)).length() - 12);
				} else {
					imm = zeroExtend(12, intToBinary(instructionTokens.get(3)));
				}

				f = zeroExtend(4, 
						Integer.toBinaryString(i.getFunction()));
				return OP + a + b + f + imm;

			}

		} else if (instructionTokens.size() == 5) {

			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));
			b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));
			Imm2 = zeroExtend(5, intToBinary(instructionTokens.get(4)));
			if (instructionTokens.get(3).contains("@")) {
				imm = Integer.toBinaryString(pc.getLableAddress(instructionTokens.get(3)));
				if (imm.length() > 12)
					imm = imm.substring(imm.length() - 12);
				imm = zeroExtend(12, imm);
			} else if (intToBinary(instructionTokens.get(3)).length() > 12) {

				imm = intToBinary(instructionTokens.get(3))
						.substring(intToBinary(instructionTokens.get(3)).length() - 12);
			} else {
				imm = zeroExtend(12, intToBinary(instructionTokens.get(3)));
			}
			f = zeroExtend(4, Integer.toBinaryString(i.getFunction()));
			return OP + a + b + f + imm + Imm2;

		} else if (instructionTokens.size() == 6) {

		}

		return null;
	}

	private String parseBType(ProgramCounter pc, int instructionInd) {
		
		InstructionObject i = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());

		if (i.getOpcode() > 7 && i.getOpcode() < 14) {
			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));

			Imm2 = zeroExtend(5, intToBinary(instructionTokens.get(2)));

			imm = instructionTokens.get(3);
			if (imm.contains("@")) {
				String S = Integer.toBinaryString(pc.getLableAddress(imm) - instructionInd);

				if (S.length() > 16)
					imm = S.substring(S.length() - 16);
				else
					imm = zeroExtend(16, S);
			} else {
				imm = zeroExtend(16, intToBinary(instructionTokens.get(3)));
			}
			return OP + a + Imm2 + imm;

		} else {

			OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));
			a = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(1))));

			if (instructionTokens.size() == 2)
				b = zeroExtend(5, intToBinary("0"));
			else
				b = zeroExtend(5, intToBinary(getRegisterNumber(instructionTokens.get(2))));

			imm = instructionTokens.get(3);
			if (imm.contains("@")) {
				String S = Integer.toBinaryString(pc.getLableAddress(imm) - instructionInd);

				if (S.length() > 16)
					imm = S.substring(S.length() - 16);
				else
					imm = zeroExtend(16, S);
			} else {
				imm = zeroExtend(16, intToBinary(instructionTokens.get(3)));
			}

			return OP + a + b + imm;
		}
	}

	private String parseJType(ProgramCounter pc) {

		InstructionObject i = instructionsListHashMap.get(instructionTokens.get(0).toUpperCase());

		
		OP = zeroExtend(6, Integer.toBinaryString(i.getOpcode()));

		imm = instructionTokens.get(1);
		if (imm.contains("@")) {
			imm = zeroExtend(26, Integer.toBinaryString(pc.getLableAddress(imm)));
		} else {
			imm = zeroExtend(26, intToBinary(instructionTokens.get(1)));
		}

		return OP + imm;

	}

	private static String getRegisterNumber(String S) {
		int registerNumber = Integer.parseInt(S.substring(1));
		if (S.substring(0).toLowerCase().contains("r") && registerNumber >= 0 && registerNumber < 32) {
			return S.substring(1);
		} else if (S.substring(0).toLowerCase().contains("t") && registerNumber >= 0 && registerNumber < 10) {

			return "1" + S.substring(1);
		} else if (S.substring(0).toLowerCase().contains("s") && registerNumber >= 0 && registerNumber < 10) {
			return "2" + S.substring(1);
		} else {
			return null;
		}

	}

	private String intToBinary(String S) {
		return Integer.toBinaryString(Integer.parseInt(S));
	}

	private String zeroExtend(int resultBitsCount, String operand) {
		String temp = "";
		for(int i = 0; i<resultBitsCount - operand.length();i++) {
			temp += "0";
		}
		temp += operand;
		return temp;
	}



	public boolean getStatus() {
		return status;
	}

	

	
}