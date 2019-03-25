import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AssemblerNotUsed {
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		
		
		
		loadAllInstructions();
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add( "add r5 = r5, 5");
		ProgramCounter pc = new ProgramCounter();
//		g = fetchInstruction(pc, instructions);
		
		//B=====================================================================================================================
		
	}
	public static String fetchInstruction(RegisterFile r, ProgramCounter pc ,ArrayList<String> instructions) {
		
//		RegisterFile r = new RegisterFile();
		for (int i = 0; i < instructions.size(); i++) {
	
			instructions.set(i, instructions.get(i).replace("[", ""));
			instructions.set(i, instructions.get(i).replace("]", ""));
			instructions.set(i, instructions.get(i).replace("=", " "));
			instructions.set(i, instructions.get(i).replace(",", " "));


			ArrayList<String> instructionArray = new ArrayList<String>();

			StringTokenizer st = new StringTokenizer(instructions.get(i), " ");

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
					BInstruction BInstruction = new BInstruction(i, instructions.get(i), instructionBinary);
					pc.getInstructionsList().add(BInstruction);
					
				}else if(instructionObject.getOpcode() >= 8 && instructionObject.getOpcode() <= 13 ) {
					String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
					String bBinary = padLeftZeros(toBinary(instructionArray.get(2)),5);
					String offset = padLeftZeros(toBinary((instructionArray.get(3))),16);
					String instructionBinary = opcodeBinary + aBinary + bBinary + offset;
					BInstruction BInstruction = new BInstruction(i, instructions.get(i), instructionBinary);
					pc.getInstructionsList().add(BInstruction);
					
				}else if(instructionObject.getOpcode() >= 14){
					String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
					String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
					String bBinary = padLeftZeros("0",5);
					String offset = padLeftZeros(toBinary((instructionArray.get(3))),16);
					String instructionBinary = opcodeBinary + aBinary + bBinary + offset;
					BInstruction BInstruction = new BInstruction(i, instructions.get(i), instructionBinary);
					pc.getInstructionsList().add(BInstruction);
				}
				
				break;
			case "J":
				
				String opcodeBinary = padLeftZeros(toBinary(String.valueOf(instructionObject.getOpcode())),6);
				String offset = padLeftZeros(toBinary(instructionArray.get(1)),26);
				String instructionBinary = opcodeBinary + offset;
				JInstruction JInstruction = new JInstruction(i, instructions.get(i), instructionBinary);
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
				String aBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(2))),5);
				String bBinary = padLeftZeros(toBinary(getRegister(instructionArray.get(1))),5);
				String fBinary = padLeftZeros(toBinary(String.valueOf((instructionObject.getFunction()))),4);
				String immBinary = padLeftZeros(toBinary(String.valueOf(instructionArray.get(3))),12);
				String instructionBinary1 = opcodeBinary1 + aBinary + bBinary + fBinary + immBinary;
				
				IInstruction IInstruction = new IInstruction(i, instructions.get(i), instructionBinary1);
				pc.getInstructionsList().add(IInstruction);
				break;
			}
			//B====================================================================================================
				

		}
		

		
		return r.toString();
	}

	
	
	
	
	






	

	private static HashMap<String,InstructionObject> instructionFormat;
	private static String g;
	
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
	

	public static String getRegister(String operand) {
		return operand.substring(1);
	}

	private static String toBinary(String operand) {
		int dd = Integer.parseInt(operand);
		String a = Integer.toBinaryString(dd);
		return a;
	}

	
}
