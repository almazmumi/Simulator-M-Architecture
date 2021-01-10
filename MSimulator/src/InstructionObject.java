
public class InstructionObject {
	private int opcode;
	private String format;
	private String name;
	private int function;
	private int x;
	public InstructionObject(String name, String format, int opcode, int function, int x) {
		this.opcode = opcode;
		this.name = name;
		this.format = format;
		this.function = function;
		this.x = x;
	}
	public int getOpcode() {
		return opcode;
	}
	public String getFormat() {
		return format;
	}
	public int getFunction() {
		return function;
	}
	public int getX() {
		return x;
	}
	
	
}
