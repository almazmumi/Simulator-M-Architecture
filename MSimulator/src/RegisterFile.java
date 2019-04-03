
public class RegisterFile {
	static int[] registersFile;
	
	public RegisterFile(){
		// Register files are an array of 31 string(Binary Value)
		registersFile = new int[32];	
	}
	
	public int getRegister(int RegisterNumber) {
		return registersFile[RegisterNumber];
	}
	
	public void setRegister(int RegisterNumber, int newValue) {
		registersFile[RegisterNumber] = newValue;
	}
	
	public int[] getRegistersFile() {
		return registersFile;
	}
	
	public void reset() {
		for (int i= 0; i< registersFile.length; i++) {
			registersFile[i] = 0;
		}
	}

	
}
