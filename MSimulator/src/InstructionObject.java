
public class InstructionObject {
	private int opcode;
	private char format;
	private String name;
	private int function;
	private int x;

	public InstructionObject(String name, char format, int opcode, int f, int x) {
		this.opcode = opcode;
		this.setName(name);
		this.format = format;
		this.function = f;
		this.x = x;		
	}
	public int getOpcode() {
		return opcode;
	}
	public char getFormat() {
		return format;
	}
	public int getFunction() {
		return function;
	}
	public int getX() {
		return x;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
