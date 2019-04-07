import java.util.StringTokenizer;

public class DataMemory {

	private static char[] MemoryArray;

	DataMemory() {
		MemoryArray = new char[8192];
	}
	
	static void MemoryInitilaizor(String Data) {
		StringTokenizer st = new StringTokenizer(Data,"\n");
		int MemoryPointer =0;
		int MemoryAlign=1;
		while(st.hasMoreTokens()) {
			String Next=st.nextToken();
			if(Next.contains("@")) {
				String S=Next.substring(Next.indexOf('.'));
				if(S.contains(".byte")) {
					S=S.substring(S.indexOf(" "));
					String[] gg=S.split(",");
					for(int i =0;i<gg.length;i++) {
						if(gg[i].contains("'")) {
							
							
						gg[i]=gg[i].replace("'","");
						gg[i]=gg[i].replace(" ","");
						MemoryArray[MemoryPointer]=gg[i].charAt(0);
						MemoryPointer++;
						MemoryPointer=MemoryPointer*MemoryAlign;
						
						}else if(gg[i].toUpperCase().contains("0X")) {
							gg[i]=gg[i].replace("0X","");
							gg[i]=gg[i].replace(" ","");
							System.out.print(gg[i]);
							int Intg=Integer.parseInt(gg[i],16);
							
							MemoryArray[MemoryPointer]=(char)Intg;
							MemoryPointer++;
							MemoryPointer=MemoryPointer*MemoryAlign;
							
						}else{
							int Intg=Integer.parseInt(gg[i]);
							if(Intg<0) {
								Intg=(16-(Intg*-1))+240;
							
							}
							MemoryArray[MemoryPointer]=(char)Intg;
							MemoryPointer++;
							MemoryPointer=MemoryPointer*MemoryAlign;
							
							
						}
						
						
					}
					
				}else if(S.contains(".hword")) {
					
				}else if(S.contains(".word")) {
					
				}else if(S.contains(".dword")) {
					
				}
			}else {
				
			}
			
		}
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

			System.out.println("Address[" + i + "]= " + (int) MemoryArray[i]);
		}
	}
	
	public void reset() {
		for (int i = 0; i < MemoryArray.length; i++) {
			MemoryArray[i] = 0;
		}
	}

}
