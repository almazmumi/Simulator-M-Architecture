
public class DataMemory {

	private static char[] MemoryArray;

	DataMemory() {
		MemoryArray = new char[8192];
	}

	static void addData(int Address, char Data) {
		MemoryArray[Address] = Data;

	}

	static void MemoryReset() {

		MemoryArray = new char[8192];
		for (int i = 0; i < 8192; i++) {
			MemoryArray[i] = (char) (0);
		}

	}

	char getData(int i) {
		return MemoryArray[i];
	}

	static void ListElemnts() {
		for (int i = 0; i < 10; i++) {

//			System.out.println("Address[" + i + "]= " + (int) MemoryArray[i]);
		}
	}
	
	public void reset() {
		for (int i = 0; i < MemoryArray.length; i++) {
			MemoryArray[i] = 0;
		}
	}

}
