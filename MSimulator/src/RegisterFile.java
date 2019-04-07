
public class RegisterFile {
	static long[] registersFile;
	
	public RegisterFile(){
		// Register files are an array of 31 string(Binary Value)
		registersFile = new long[32];	
	}
	
	public long getRegister(int RegisterNumber) {
		return registersFile[RegisterNumber];
	}
	
	public void setRegister(int RegisterNumber, long newValue) {
		registersFile[RegisterNumber] = newValue;
	}
	
	public long[] getRegistersFile() {
		return registersFile;
	}
	
	public void reset() {
		for (int i= 0; i< registersFile.length; i++) {
			registersFile[i] = 0;
		}
	}

	
}
