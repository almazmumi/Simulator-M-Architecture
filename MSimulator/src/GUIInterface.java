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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;

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
import java.awt.Dimension;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.JSlider;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class GUIInterface extends JFrame {

	// TODO make error code area

	private DataMemory mem;
	private ProgramCounter pc;
	private RegisterFile rf;
	private ArrayList<String> instArray;
	private JPanel contentPane;
	private JTable table;

	private String RegistersOption = "Decimal";

	private String MachineCodeOption = "Binary";
	private int frequencySpeed = -1;
	private JPanel textCodeArea;
	private JTextPane machineCodeArea;
	private JSplitPane editTabSplitPane;
	private JTextPane inputCodeTextPane;
	private JTextPane rowLines;
	private JInternalFrame dataSegmentIF;
	private JInternalFrame textSegmentIF;
	private JTable textSegmentTable;
	private JTable dataSegmentTable;

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

		// =========================================================================================
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		// =========================================================================================

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

		JMenu mnEditView = new JMenu("Edit view");
		mnEditView.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEdit.add(mnEditView);
		
		JCheckBoxMenuItem chckbxmntmMachineCode = new JCheckBoxMenuItem("Machine code");
		chckbxmntmMachineCode.setSelected(true);
		chckbxmntmMachineCode.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxmntmMachineCode.isSelected()){
					editTabSplitPane.setRightComponent(machineCodeArea);
				}else{
					editTabSplitPane.setRightComponent(null);
				}
			}
		});
		chckbxmntmMachineCode.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEditView.add(chckbxmntmMachineCode);

		JCheckBoxMenuItem chckbxmntmRegisters = new JCheckBoxMenuItem("Registers");
		chckbxmntmRegisters.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEditView.add(chckbxmntmRegisters);

		JCheckBoxMenuItem chckbxmntmDataMemory = new JCheckBoxMenuItem("Data memory");
		chckbxmntmDataMemory.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEditView.add(chckbxmntmDataMemory);

		JMenu mnRegistersValue = new JMenu("Registers value");
		mnRegistersValue.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEdit.add(mnRegistersValue);

		ButtonGroup registersOption = new ButtonGroup();
		JRadioButtonMenuItem rdbtnmntmBinary = new JRadioButtonMenuItem("Binary");
		rdbtnmntmBinary.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		registersOption.add(rdbtnmntmBinary);
		mnRegistersValue.add(rdbtnmntmBinary);

		JRadioButtonMenuItem rdbtnmntmHex = new JRadioButtonMenuItem("Hex");
		rdbtnmntmHex.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		registersOption.add(rdbtnmntmHex);
		mnRegistersValue.add(rdbtnmntmHex);

		JRadioButtonMenuItem rdbtnmntmDecimal = new JRadioButtonMenuItem("Decimal");
		rdbtnmntmDecimal.setSelected(true);
		rdbtnmntmDecimal.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		registersOption.add(rdbtnmntmDecimal);
		mnRegistersValue.add(rdbtnmntmDecimal);

		ActionListener registersOptionActionListeners = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButtonMenuItem aButton = (JRadioButtonMenuItem) e.getSource();
				RegistersOption = aButton.getText();
				System.out.println(MachineCodeOption);
				updateRegisterFileTable(table, rf, pc);

			}
		};

		JMenu mnMachineCodeValue = new JMenu("Machine code value");
		mnMachineCodeValue.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnEdit.add(mnMachineCodeValue);

		rdbtnmntmBinary.addActionListener(registersOptionActionListeners);
		rdbtnmntmHex.addActionListener(registersOptionActionListeners);
		rdbtnmntmDecimal.addActionListener(registersOptionActionListeners);

		ButtonGroup machineCodeOption = new ButtonGroup();
		JRadioButtonMenuItem rdbtnmntmBinaryMC = new JRadioButtonMenuItem("Binary");
		rdbtnmntmBinaryMC.setSelected(true);
		rdbtnmntmBinaryMC.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		machineCodeOption.add(rdbtnmntmBinaryMC);
		mnMachineCodeValue.add(rdbtnmntmBinaryMC);

		JRadioButtonMenuItem rdbtnmntmHexMC = new JRadioButtonMenuItem("Hex");
		rdbtnmntmHexMC.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		machineCodeOption.add(rdbtnmntmHexMC);
		mnMachineCodeValue.add(rdbtnmntmHexMC);

		ActionListener machineCodeOptionActionListeners = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButtonMenuItem aButton = (JRadioButtonMenuItem) e.getSource();
				MachineCodeOption = aButton.getText();

			}
		};

		rdbtnmntmBinaryMC.addActionListener(machineCodeOptionActionListeners);
		rdbtnmntmHexMC.addActionListener(machineCodeOptionActionListeners);

		// ==========================================================================================

		// =========================================================================================
		// Initialise the hash map for all instructions type (ADD, J, JAL, SUB, ...)
		try {
			Assembler.initializeCommands();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		buttonPanel.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		JLabel lblRunSpeedAt = new JLabel("Frequency Speed, maximum");
		
		
		
		lblRunSpeedAt.setHorizontalAlignment(SwingConstants.CENTER);
		lblRunSpeedAt.setFont(new Font("Tahoma", Font.PLAIN, 19));
		panel.add(lblRunSpeedAt);
		
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		
				slider.setValue(30);
				slider.setPaintTicks(true);
				slider.setMajorTickSpacing(5);
				slider.setMaximum(30);
				panel.add(slider);
				slider.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(slider.getValue() == 30) {
							lblRunSpeedAt.setText("Frequency Speed, maximum");
							frequencySpeed = -1;
						}else{
							lblRunSpeedAt.setText("Frequency Speed, " + slider.getValue() + " inst/sec");
							frequencySpeed = slider.getValue();
						}
					}
				});
		// =========================================================================================

		// =========================================================================================
		JButton run = new JButton("Run");
		run.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(run);

		JButton debug = new JButton("Trace");
		debug.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(debug);

		JButton btnReset = new JButton("Reset");

		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(btnReset);

		JLabel lblPcvalue = new JLabel("PC = 0");
		lblPcvalue.setFont(new Font("Tahoma", Font.PLAIN, 19));
		buttonPanel.add(lblPcvalue);

		JSplitPane firstSplitPane = new JSplitPane();
		firstSplitPane.setResizeWeight(0.9);
		contentPane.add(firstSplitPane, BorderLayout.CENTER);

		JSplitPane secondSplitPane = new JSplitPane();
		secondSplitPane.setResizeWeight(0.8);
		firstSplitPane.setLeftComponent(secondSplitPane);
		secondSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		secondSplitPane.setLeftComponent(tabbedPane);

		editTabSplitPane = new JSplitPane();
		editTabSplitPane.setResizeWeight(0.999);
		tabbedPane.addTab("Edit", null, editTabSplitPane, null);

		machineCodeArea = new JTextPane();
		machineCodeArea.setFont(new Font("Segoe UI Historic", Font.PLAIN, 22));
		editTabSplitPane.setRightComponent(machineCodeArea);

		JScrollPane editorPane = new JScrollPane();
		editTabSplitPane.setLeftComponent(editorPane);

		inputCodeTextPane = new JTextPane();
		inputCodeTextPane.setFont(new Font("Segoe UI Historic", Font.PLAIN, 22));
		editorPane.setViewportView(inputCodeTextPane);

		rowLines = new JTextPane();
		rowLines.setText(
				"1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n10\r\n11\r\n12\r\n13\r\n14\r\n15\r\n16\r\n17\r\n18\r\n19\r\n20\r\n21\r\n22\r\n23\r\n24\r\n25");
		rowLines.setFont(new Font("Segoe UI Historic", Font.PLAIN, 22));
		rowLines.setEnabled(false);
		rowLines.setEditable(false);
		editorPane.setRowHeaderView(rowLines);

		JDesktopPane executeTabDesktopPane = new JDesktopPane();
		executeTabDesktopPane.setBackground(Color.WHITE);
		executeTabDesktopPane.setSize(tabbedPane.getSize());
		System.out.println(tabbedPane.getSize().height);
		tabbedPane.addTab("Execute", null, executeTabDesktopPane, null);

		textSegmentIF = new JInternalFrame("Text Segment");
		textSegmentIF.setBorder(new LineBorder(new Color(0, 0, 0)));
		textSegmentIF.setMaximizable(true);
		textSegmentIF.setBounds(0, 0, 1295, 350);
		executeTabDesktopPane.add(textSegmentIF);

		dataSegmentIF = new JInternalFrame("Data Segment");
		dataSegmentIF.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataSegmentIF.setBounds(0, 350, 1295, 350);
		executeTabDesktopPane.add(dataSegmentIF);
		dataSegmentIF.setResizable(true);
		dataSegmentIF.setMaximizable(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		dataSegmentIF.getContentPane().add(scrollPane_1, BorderLayout.CENTER);
		
		dataSegmentTable = new JTable();
		dataSegmentTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Address", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		dataSegmentTable.getColumnModel().getColumn(0).setPreferredWidth(85);
		dataSegmentTable.getColumnModel().getColumn(1).setPreferredWidth(73);
		dataSegmentTable.getColumnModel().getColumn(2).setPreferredWidth(111);
		dataSegmentTable.getColumnModel().getColumn(3).setPreferredWidth(68);
		scrollPane_1.setViewportView(dataSegmentTable);
		textSegmentIF.setResizable(true);
		textSegmentIF.setMaximizable(true);
		
		JScrollPane scrollPane = new JScrollPane();
		textSegmentIF.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textSegmentTable = new JTable();
		textSegmentTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Address", "Code", "Basic", "Source Code"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		textSegmentTable.getColumnModel().getColumn(0).setPreferredWidth(116);
		textSegmentTable.getColumnModel().getColumn(1).setPreferredWidth(131);
		textSegmentTable.getColumnModel().getColumn(2).setPreferredWidth(262);
		textSegmentTable.getColumnModel().getColumn(3).setPreferredWidth(347);
		scrollPane.setViewportView(textSegmentTable);
		dataSegmentIF.setVisible(true);
		textSegmentIF.setVisible(true);
		
		
		JScrollPane registerFilePane = new JScrollPane();
		firstSplitPane.setRightComponent(registerFilePane);
		
		
		// =========================================================================================

		// =========================================================================================
		table = new JTable();
		registerFilePane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setModel(new DefaultTableModel(
				new Object[][] { { "R0", "R0", "0" }, { "R1", "R1", "0" }, { "R2", "R2", "0" }, { "R3", "R3", "0" },
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
		textCodeArea = new JPanel();
		contentPane.add(textCodeArea, BorderLayout.NORTH);
		textCodeArea.setLayout(new BorderLayout(0, 0));
		// =========================================================================================

		// Initialise the program counter, registerFile and dataMemory.
		pc = new ProgramCounter();
		rf = new RegisterFile();
		mem = new DataMemory();

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.reset();
				rf.reset();
				mem.reset();
				instArray.clear();
				updateRegisterFileTable(table, rf, pc);
				lblPcvalue.setText("PC = 0");
				machineCodeArea.setText("");
				StyledDocument doc = (StyledDocument) rowLines.getDocument();
				Style style = rowLines.addStyle("MyHilite", null);
				StyleConstants.setBold(style, false);
				doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);
			}
		});

		// =========================================================================================
		instArray = new ArrayList<String>();

		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Reinitialise the ProgramCounter and RegisterFile */
				
				
				
				CodeRunningThread nnn = new CodeRunningThread();
				nnn.start();

			}
		});

		debug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!pc.isItRunning()) {
					/* Reinitialise the ProgramCounter and RegisterFile */
					pc.reset();
					rf.reset();
					mem.reset();
					machineCodeArea.setText("");

					// To style the lines number
					StyledDocument doc = (StyledDocument) rowLines.getDocument();
					Style style = rowLines.addStyle("MyHilite", null);
					StyleConstants.setBold(style, false);
					doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);
					//-----------------------------------------------------------------------------------

					/* Convert the text to array list of assembly instructions */

					// to replace all blank lines
					String[] a = inputCodeTextPane.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "")
							.split(System.getProperty("line.separator"));
					// ---------------------------------------------------------------------------------------------------------------

					// to save all @lables in programCounter
					for (int i = 0; i < a.length; i++) {
						a[i] = a[i].split("\\//")[0];
						String temp = a[i].split(" ")[0];
						if (temp.contains("@")) {
							pc.addLableAddress(a[i].split(" ")[0], i);
							a[i] = a[i].replace(temp, "").trim().equals("") ? null : a[i].replace(temp, "").trim();
						}
						if (a[i] != null) {
							instArray.add(a[i]);
						}
					}

					/* Convert assembly language into machine code */
					Assembler.SetInstructionInPC(pc, instArray);

					/* Print Machine Code Binary */
					String binaryString = pc.getInstructionsList().get(pc.getProgramCounter()).getInstructionBinary();
					int decimal = Integer.parseUnsignedInt(binaryString, 2);
					String hexStr = Integer.toUnsignedString(decimal, 16);
					if (MachineCodeOption.equals("Hex")) {

						machineCodeArea.setText(hexStr + "");

					} else if (MachineCodeOption.equals("Binary")) {

						machineCodeArea.setText(binaryString + "");

					}

				} else {
					/* Print Machine Code Binary */
					String binaryString = pc.getInstructionsList().get(pc.getProgramCounter()).getInstructionBinary();
					int decimal = Integer.parseUnsignedInt(binaryString, 2);
					String hexStr = Integer.toUnsignedString(decimal, 16);
					if (MachineCodeOption.equals("Hex")) {
						machineCodeArea.setText(machineCodeArea.getText() + "\r\n" + hexStr.toUpperCase());

					} else if (MachineCodeOption.equals("Binary")) {

						machineCodeArea.setText(machineCodeArea.getText() + "\r\n" + binaryString);

					}

				}

				lblPcvalue.setText("PC = " + pc.getProgramCounter());
				StyledDocument doc = (StyledDocument) rowLines.getDocument();
				int start = pc.getProgramCounter() * 2;
				int end = start + 2;
				Style style = rowLines.addStyle("MyHilite", null);

				StyleConstants.setBold(style, false);
				doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);
				StyleConstants.setBold(style, true);
				doc.setCharacterAttributes(start, end - start, style, true);

				(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf, mem);
				updateRegisterFileTable(table, rf, pc);

				if (pc.getProgramCounter() == pc.getInstructionsList().size()) {
					System.out.println("Here");
					pc.reset();
					rf.reset();
					instArray.clear();
				}

			}
		});

		// =========================================================================================
	}

	private void updateRegisterFileTable(JTable t, RegisterFile r, ProgramCounter pc) {
		if (RegistersOption.equals("Decimal"))
			for (int i = 0; i < 32; i++) {
				t.getModel().setValueAt(r.getRegister(i), i, 2);
			}
		else if (RegistersOption.equals("Hex"))
			for (int i = 0; i < 32; i++) {

				t.getModel().setValueAt(Integer.toString(r.getRegister(i), 16).toUpperCase(), i, 2);
			}
		else if (RegistersOption.equals("Binary"))
			for (int i = 0; i < 32; i++) {
				t.getModel().setValueAt(Integer.toBinaryString(r.getRegister(i)), i, 2);
			}
	}




class CodeRunningThread extends Thread {
	   private Thread t;
	   private String threadName="runningCodeThread";
	   private int count = 0; 
	   
	   CodeRunningThread() {
	      
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
			/* Reinitialise the ProgramCounter and RegisterFile */
			pc.reset();
			rf.reset();
			mem.reset();
			machineCodeArea.setText("");

			// To style the lines number
			StyledDocument doc = (StyledDocument) rowLines.getDocument();
			Style style = rowLines.addStyle("MyHilite", null);
			StyleConstants.setBold(style, false);
			doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);
			// -----------------------------------------------------------------------------------

			/* Convert the text to array list of assembly instructions */

			// to replace all blank lines
			String[] a = inputCodeTextPane.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "")
					.split(System.getProperty("line.separator"));
			// ---------------------------------------------------------------------------------------------------------------

			// to save all @lables in programCounter
			for (int i = 0; i < a.length; i++) {
				a[i] = a[i].split("\\//")[0];
				String temp = a[i].split(" ")[0];
				if (temp.contains("@")) {
					pc.addLableAddress(a[i].split(" ")[0], i);
					a[i] = a[i].replace(temp, "").trim().equals("") ? null : a[i].replace(temp, "").trim();
				}
				if (a[i] != null) {
					instArray.add(a[i]);
				}
			}

			/* Convert assembly language into machine code */
			Assembler.SetInstructionInPC(pc, instArray);

			// Execute the instruction listmachineCodeArea.setText("");
			while (pc.getProgramCounter() < pc.getInstructionsList().size()) {
				
				/* Print Machine Code Binary */
				String binaryString = pc.getInstructionsList().get(pc.getProgramCounter()).getInstructionBinary();
				int decimal = Integer.parseUnsignedInt(binaryString, 2);
				String hexStr = Integer.toUnsignedString(decimal, 16);
				if (MachineCodeOption.equals("Hex")) {
					if (machineCodeArea.getText().equals("")) {
						machineCodeArea.setText(hexStr + "");
					} else {
						machineCodeArea.setText(machineCodeArea.getText() + "\r\n" + hexStr.toUpperCase());
					}
				} else if (MachineCodeOption.equals("Binary")) {
					if (machineCodeArea.getText().equals("")) {
						machineCodeArea.setText(binaryString + "");
					} else {
						machineCodeArea.setText(machineCodeArea.getText() + "\r\n" + binaryString);
					}
				}

				// Execute Instruction
				(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf, mem);
				updateRegisterFileTable(table, rf, pc);
				if(frequencySpeed != -1 && count == frequencySpeed) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					count++;
				}
			}

			pc.reset();
			instArray.clear();
			count = 0;

	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	}
}