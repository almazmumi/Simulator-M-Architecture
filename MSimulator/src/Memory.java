
public class Memory {
private static char [] MemoryArray ;


Memory() {
	 MemoryArray = new char[8192];
}
static void AddData(int Address,char Data) {
	MemoryArray[Address]=Data;
	
}

static void MemoryReset() {
	
	MemoryArray = new char[8192];
	for(int i=0;i<8192;i++) {
		MemoryArray[i]=(char)(0);
	}
	
}


char GetData(int i) {
	return MemoryArray[i];
}
static void ListElemnts() {
	for( int i =0;i<10;i++) {
		
		System.out.println("Address["+i+"]= "+(int)MemoryArray[i]);
	}
}


}
