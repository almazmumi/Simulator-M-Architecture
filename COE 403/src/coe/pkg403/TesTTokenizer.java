import java.util.StringTokenizer;  
public class TesTTokenizer {
	
	
	
public static void main(String[]args) {
	
	
	
	 StringTokenizer st = new StringTokenizer("add r20  r2       r4 "," ");  
     while (st.hasMoreTokens()) {  
         System.out.println(st.nextToken());  
     }  
	
	
	
	
	
	
	
	
	
}
}
