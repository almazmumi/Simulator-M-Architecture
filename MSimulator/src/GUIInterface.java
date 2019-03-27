import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUIInterface extends JFrame {
	private ProgramCounter pc;
	private RegisterFile rf;
	private ArrayList<String> instArray;
	private JPanel contentPane;
	private JTable table;
	private int count = -1; // for btnCompileLineByLine
	private boolean firstTime = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIInterface frame = new GUIInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIInterface() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Menu Items
		// ==================================================================================
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmNew);

		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmNewMenuItem);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmSaveAs);

		JMenuItem mntmAboutUs = new JMenuItem("About us");
		mntmAboutUs.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmAboutUs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnFile.add(mntmExit);

		JMenu mnAssembler = new JMenu("Assembler");
		mnAssembler.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(mnAssembler);

		JMenuItem mntmGetBinaryCode = new JMenuItem("Get Binary Code");
		mntmGetBinaryCode.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnAssembler.add(mntmGetBinaryCode);

		JMenu mnSimulation = new JMenu("Simulation");
		mnSimulation.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(mnSimulation);

		JMenuItem mntmGetStatistic = new JMenuItem("Get Statistic");
		mntmGetStatistic.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnSimulation.add(mntmGetStatistic);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(mnEdit);

		JMenu mnEditView = new JMenu("Edit View");
		mnEditView.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEdit.add(mnEditView);

		JCheckBoxMenuItem chckbxmntmRegisters = new JCheckBoxMenuItem("Registers");
		chckbxmntmRegisters.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEditView.add(chckbxmntmRegisters);

		JCheckBoxMenuItem chckbxmntmDataMemory = new JCheckBoxMenuItem("Data memory");
		chckbxmntmDataMemory.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEditView.add(chckbxmntmDataMemory);
		// ==========================================================================================

		// =========================================================================================
		// Initialise the hash map for all instructions type (ADD, J, JAL, SUB, ...)
		try {
			Assembler.initializeCommands();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// =========================================================================================

		// =========================================================================================
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		// =========================================================================================

		// =========================================================================================
		JButton btnCompileAll = new JButton("Compile All");
		btnCompileAll.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(btnCompileAll);
		JButton btnCompileOneLine = new JButton("Compile Line by Line");
		btnCompileOneLine.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(btnCompileOneLine);
		// =========================================================================================

		// =========================================================================================
		JPanel textCodeArea = new JPanel();
		contentPane.add(textCodeArea, BorderLayout.CENTER);
		textCodeArea.setLayout(new BorderLayout(0, 0));
		JTextPane txtpnCodeview = new JTextPane();
		textCodeArea.add(txtpnCodeview, BorderLayout.CENTER);
		txtpnCodeview.setFont(new Font("Tahoma", Font.PLAIN, 22));

		JTextPane rowLines = new JTextPane();
		rowLines.setFont(new Font("Tahoma", Font.PLAIN, 22));
		rowLines.setText(
				"1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n10\r\n11\r\n12\r\n13\r\n14\r\n15\r\n16\r\n17\r\n18\r\n19\r\n20\r\n21\r\n22\r\n23\r\n24\r\n25\r\n26\r\n27\r\n28\r\n29\r\n30\r\n31\r\n32\r\n33\r\n34\r\n35\r\n36\r\n37\r\n38\r\n39\r\n40\r\n41\r\n42\r\n43\r\n44\r\n45\r\n46\r\n47\r\n48\r\n49\r\n50\r\n51\r\n52\r\n53\r\n54\r\n55\r\n56\r\n57\r\n58\r\n59\r\n60\r\n61\r\n62\r\n63\r\n64\r\n65\r\n66\r\n67\r\n68\r\n69\r\n70\r\n71\r\n72\r\n73\r\n74\r\n75\r\n76\r\n77\r\n78\r\n79\r\n80\r\n81\r\n82\r\n83\r\n84\r\n85\r\n86\r\n87\r\n88\r\n89\r\n90\r\n91\r\n92\r\n93\r\n94\r\n95\r\n96\r\n97\r\n98\r\n99\r\n100");
		rowLines.setEnabled(false);
		rowLines.setEditable(false);
		textCodeArea.add(rowLines, BorderLayout.WEST);

		Document d = txtpnCodeview.getDocument();

		// To change the instruction name style
		d.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				

			}

			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void insertUpdate(DocumentEvent e) {
				
				
				
				
				
				
				
				
				
				
				char c = txtpnCodeview.getText().toString().charAt(txtpnCodeview.getText().toString().length() - 1);
				if (c == ' ') {
					String lines[] = txtpnCodeview.getText().toString().split("\\r?\\n");

					Runnable doAssist = new Runnable() {
						@Override
						public void run() {
//							StyledDocument doc = (StyledDocument) txtpnCodeview.getDocument();
//							int start = 0;
//							for (int i = 0; i < lines.length - 1; i++) {
//								start += lines[i].length() + 1;
//							}
//
//							int end = start + lines[lines.length - 1].split(" ")[0].length() + 1;
//
//							StyledDocument docc = txtpnCodeview.getStyledDocument();
//
//							Style style = txtpnCodeview.addStyle("MyHilite", null);
//
//							// style = textPane.getStyle("MyHilite");
//							StyleConstants.setBold(style, true);
//							doc.setCharacterAttributes(start, end - start, style, false);
						}
					};
					SwingUtilities.invokeLater(doAssist);

				}
				System.out.println("2");


			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		// =========================================================================================

		// =========================================================================================
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(table, BorderLayout.EAST);
		table.setModel(new DefaultTableModel(
				new Object[][] { { "R1", "R0", "0" }, { "R0", "R1", "0" }, { "R2", "R2", "0" }, { "R3", "R3", "0" },
						{ "R4", "R4", "0" }, { "R5", "R5", "0" }, { "R6", "R6", "0" }, { "R7", "R7", "0" },
						{ "R8", "R8", "0" }, { "R9", "R9", "0" }, { "R10", "T0", "0" }, { "R11", "T1", "0" },
						{ "R12", "T2", "0" }, { "R13", "T3", "0" }, { "R14", "T4", "0" }, { "R15", "T5", "0" },
						{ "R16", "T6", "0" }, { "R17", "T7", "0" }, { "R18", "T8", "0" }, { "R19", "T9", "0" },
						{ "R20", "S0", "0" }, { "R21", "S1", "0" }, { "R22", "S2", "0" }, { "R23", "S3", "0" },
						{ "R24", "S4", "0" }, { "R25", "S5", "0" }, { "R26", "S6", "0" }, { "R27", "S7", "0" },
						{ "R28", "S8", "0" }, { "R29", "FP", "0" }, { "R30", "SP", "0" }, { "R31", "LR", "0" }, },
				new String[] { "R#", "R Name", "Register Number" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(90);
		table.setRowHeight(28);
		// =========================================================================================

		// =========================================================================================
		JTextPane machineCodeArea = new JTextPane();
		machineCodeArea.setFont(new Font("Tahoma", Font.PLAIN, 22));
		machineCodeArea.setText(null);
		machineCodeArea.setEditable(false);
		contentPane.add(machineCodeArea, BorderLayout.WEST);
		// =========================================================================================

		// =========================================================================================
		instArray = new ArrayList<String>();

		btnCompileAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				/* Convert the text to array list of assembly instructions */
				String[] a = txtpnCodeview.getText().split(System.getProperty("line.separator"));
				for (int i = 0; i < a.length; i++) {
					if (!a[i].equals(""))
						instArray.add(a[i]);
				}
				
				/* Reinitialise the ProgramCounter and RegisterFile */
				pc = new ProgramCounter();
				rf = new RegisterFile();

				/* Convert assembly language into machine code */
				Assembler.SetInstructionInPC(pc, instArray);

				// Execute the instruction listmachineCodeArea.setText("");
				while (pc.getProgramCounter() < pc.getInstructionsList().size()) {
					
					
					/*Print Machine Code Binary*/
					
					if(machineCodeArea.getText().equals("")) {
						machineCodeArea.setText(( pc.getInstructionsList().get(pc.getProgramCounter()))
										.getInstructionBinary());
					} else {
						machineCodeArea.setText(machineCodeArea.getText() + "\r\n"
							+ (pc.getInstructionsList().get(pc.getProgramCounter()))
									.getInstructionBinary());
					}
					
					// Execute Instruction
//					if (pc.getInstructionsList().get(pc.getProgramCounter()).getClass().getName()
//							.equals("IInstruction"))
//						(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf);
//					else if (pc.getInstructionsList().get(pc.getProgramCounter()).getClass().getName()
//							.equals("RInstruction"))
					
					(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf);
					
					updateRegisterFileTable(table, rf, pc);
					
				}
				
				pc.setProgramCounter(0);
				instArray.clear();
				
			}
		});

		btnCompileOneLine.addActionListener(new ActionListener() {

			

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(firstTime) {
					/* Convert the text to array list of assembly instructions */
					String[] a = txtpnCodeview.getText().split(System.getProperty("line.separator"));
					for (int i = 0; i < a.length; i++) {
						if (!a[i].equals(""))
							instArray.add(a[i]);
					}
					
					/* Reinitialise the ProgramCounter and RegisterFile */
					pc = new ProgramCounter();
					rf = new RegisterFile();
	
					/* Convert assembly language into machine code */
					Assembler.SetInstructionInPC(pc, instArray);
					machineCodeArea.setText("");
					firstTime = false;
					machineCodeArea.setText((pc.getInstructionsList().get(pc.getProgramCounter())).getInstructionBinary());
				}else {
					machineCodeArea.setText(machineCodeArea.getText() + "\r\n"
							+ (pc.getInstructionsList().get(pc.getProgramCounter())).getInstructionBinary());
				}
				
				
				StyledDocument doc = (StyledDocument) rowLines.getDocument();
				
				int start = pc.getProgramCounter()*2;
				int end = start+2;
				StyledDocument docc = rowLines.getStyledDocument();
				Style style = rowLines.addStyle("MyHilite", null);

				StyleConstants.setForeground(style, Color.RED);
				StyleConstants.setBold(style, true);
				doc.setCharacterAttributes(start, end - start, style, true);				
				

				(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf);
				updateRegisterFileTable(table, rf, pc);

				if(pc.getProgramCounter() == pc.getInstructionsList().size()) {
					pc.setProgramCounter(0);
					firstTime = true;
					instArray.clear();
				}
				
				
				

			}
		});

		// =========================================================================================
	}

	private void updateRegisterFileTable(JTable t, RegisterFile r, ProgramCounter pc) {
		for (int i = 0; i < 32; i++) {
			t.getModel().setValueAt(r.getRegister(i), i, 2);
		}
	}
}
