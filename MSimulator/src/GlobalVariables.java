import javax.swing.JEditorPane;
import javax.swing.JTable;

public class GlobalVariables {

	static ProgramCounter pc;
	static RegisterFile rf;
	static DataMemory dm;
	
	
	static String dataBaseAddress;
	static String textBaseAddress;
	static String stackBaseAddress;
	
	
	static JEditorPane IOEditorPane;
	static JTable textSegmentTable;
	static JTable dataSegmentTable;
	static JTable registerFileTable;
}
