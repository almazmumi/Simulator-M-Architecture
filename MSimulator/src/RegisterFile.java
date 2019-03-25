
public class RegisterFile {
	static int[] registersFile;
	
	public RegisterFile(){
		// Register files are an array of 31 string(Binary Value)
		registersFile = new int[31];	
	}
	
	public int getRegister(int RegisterNumber) {
		return registersFile[RegisterNumber];
	}
	
	public void setRegister(int RegisterNumber, int newValue) {
		registersFile[RegisterNumber] = newValue;
	}
	
	@Override
	public String toString() {
		String r = "<html> ";
		for (int i = 0; i < registersFile.length; i++) {
			r += "R"+ i + ": "+ registersFile[i] + "<br>";
		}
		r += r + "</html>";
		return r;
	}
	
}
