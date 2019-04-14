import java.util.StringTokenizer;

public class DataMemory {

	private static char[] MemoryArray;

	DataMemory() {
		MemoryArray = new char[8192];
	}

	static void addData(int Address, char Data) {
		MemoryArray[Address] = Data;

	}
	
	static void Initilaizor(String Data) {
		String [] ArrayData =Data.split("\n");
		int IndexPointer=0;
		int Alignment =0;
		 MemoryArray[0]=(char)255;
		for(int i = 0 ; i<ArrayData.length;i++) {
			if(ArrayData[i].charAt(0)=='@') {
				String Lable = ArrayData[i].substring(0,ArrayData[i].trim().indexOf(' ')).trim();
				ArrayData[i]=ArrayData[i].substring(ArrayData[i].trim().indexOf(' ')+1).trim();
				String Type = ArrayData[i].substring(0,ArrayData[i].trim().indexOf(' ')).trim();
				Type=Type.replace(".", "");
				ArrayData[i]=ArrayData[i].substring(ArrayData[i].trim().indexOf(' ')+1).trim();
				
				 StringTokenizer st = new StringTokenizer(ArrayData[i],",");  
				 if(Type.trim().equalsIgnoreCase("byte")) {

				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 if(Token.contains("'")||Token.contains("\"")) {
				    		 Token=Token.replace("'", "");
				    		 
				    		 for(int j =0;j<Token.trim().length();j++) {
				    			 MemoryArray[IndexPointer]=Token.charAt(j);
				    			 IndexPointer=IndexPointer+1;
				    			 
				    		 }
				    		 
				    	 }else if(Token.toLowerCase().contains("0x")) {
				    		 Token=Token.toLowerCase().replace("0x", "");

				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token, 16);
				    		 IndexPointer=IndexPointer+1;

				    		 
				    	 }else if(Token.contains("\"")){
				    		 Token=Token.replace("\"", ""); 
				    		 for(int j =0;j<Token.trim().length();j++) {
				    			 MemoryArray[IndexPointer]=Token.charAt(j);
				    			 IndexPointer=IndexPointer+1;
				    			 
				    		 }
				    		 MemoryArray[IndexPointer]=(char)0;
				    		 IndexPointer=IndexPointer+1;
				    		 
				    		 
				    	 }
				    	 else{
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 MemoryArray[IndexPointer]=(char)S;
			    			 IndexPointer=IndexPointer+1;
				    	 }
				     } 
					
				}else if(Type.trim().equalsIgnoreCase("hword")) {
					 System.out.print("2");
				    while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {
				    	 
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Integer.toHexString(S);
				    		 System.out.print(SS);
				    			if(SS.length()>4) {
				    				SS=SS.substring(SS.length()-4);
				    			}
				    			
				    			for (int k = 0; i < 4 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}

					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				     }  
					
				}else if(Type.trim().equalsIgnoreCase("word")) {
					
				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");

				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(4,6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {
				    	 
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Integer.toHexString(S);

				    			if(SS.length()>8) {
				    				SS=SS.substring(SS.length()-8);
				    			}
				    			
				    			for (int k = 0; i < 8 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}

					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(4,6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2,4), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				    	 
				     } 
					
				}else if(Type.trim().equalsIgnoreCase("dword")) {

				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(14), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(12,14), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(10,12), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(8,10), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(6,8), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(4,6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {

				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Long.toHexString(S);
				    			if(SS.length()>16) {
				    				SS=SS.substring(SS.length()-16);
				    			}
				    			
				    			for (int k = 0; i < 16 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}
				    			
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(14), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(12,14), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(10,12), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(8,10), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(6,8), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(4,6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2,4), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				    	 
				     } 
					
				}
				
				
				
				
			}else {
				String Type = ArrayData[i].substring(0,ArrayData[i].trim().indexOf(' ')).trim();
				ArrayData[i]=ArrayData[i].substring(ArrayData[i].trim().indexOf(' ')+1).trim();
				Type=Type.replace(".", "");
				 StringTokenizer st = new StringTokenizer(ArrayData[i],",");  
				 if(Type.contains("byte")) {
				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 if(Token.contains("'")||Token.contains("\"")) {
				    		 Token=Token.replace("'", "");
				    		 
				    		 for(int j =0;j<Token.trim().length();j++) {
				    			 MemoryArray[IndexPointer]=Token.charAt(j);
				    			 IndexPointer=IndexPointer+1;
				    			 
				    		 }
				    		 
				    	 }else if(Token.toLowerCase().contains("0x")) {
				    		 Token=Token.toLowerCase().replace("0x", "");
				    		 System.out.print(Token);
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token, 16);
				    		 IndexPointer=IndexPointer+1;

				    		 
				    	 }else if(Token.contains("\"")){
				    		 Token=Token.replace("\"", ""); 
				    		 for(int j =0;j<Token.trim().length();j++) {
				    			 MemoryArray[IndexPointer]=Token.charAt(j);
				    			 IndexPointer=IndexPointer+1;
				    			 
				    		 }
				    		 MemoryArray[IndexPointer]=(char)0;
				    		 IndexPointer=IndexPointer+1;
				    		 
				    		 
				    	 }
				    	 else{
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 MemoryArray[IndexPointer]=(char)S;
			    			 IndexPointer=IndexPointer+1;
				    	 }
				     } 
					
				}else if(Type.toLowerCase().contentEquals("hword")) {
				    while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {
				    	 
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Integer.toHexString(S);
				    			if(SS.length()>4) {
				    				SS=SS.substring(SS.length()-4);
				    			}
				    			
				    			for (int k = 0; i < 4 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}

					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				     }  
					
				}else if(Type.toLowerCase().contentEquals("word")) {
				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");

				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(4,6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {
				    	 
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Long.toHexString(S);
				    			if(SS.length()>8) {
				    				SS=SS.substring(SS.length()-8);
				    			}
				    			
				    			for (int k = 0; i < 8 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}

					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(4,6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2,4), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				    	 
				     } 
					
				}else if(Type.toLowerCase().contentEquals("dword")) {
				     while (st.hasMoreTokens()) {
				    	 String Token = st.nextToken();
				    	 
				    	 if(Token.contains("0x")) {
				    		 Token=Token.replace("0x", "");
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(14), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(12,14), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(10,12), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(8,10), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(6,8), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(4,6), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(2,4), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(Token.substring(0,2), 16);
				    		 IndexPointer=IndexPointer+1;
				    		 
				    	 }else {
				    	 
				    		 
				    		 int S = Integer.parseInt(Token);
				    		 String SS=Integer.toHexString(S);
				    			if(SS.length()>16) {
				    				SS=SS.substring(SS.length()-16);
				    			}
				    			
				    			for (int k = 0; i < 16 - SS.length(); k++) {
				    				SS = "0"+SS;
				    			}
				    			
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(14), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(12,14), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(10,12), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(8,10), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(6,8), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(4,6), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(2,4), 16);
					    		 IndexPointer=IndexPointer+1;
					    		 MemoryArray[IndexPointer]=(char)Integer.parseInt(SS.substring(0,2), 16);
					    		 IndexPointer=IndexPointer+1;
				    	 }
				    	 
				     } 
					
				}
				
			}
		}

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
