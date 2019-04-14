import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
import java.awt.Frame;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

@SuppressWarnings("serial")
public class GUIInterface extends JFrame {

	// TODO make error code area

	private DataMemory mem;
	private ProgramCounter pc;
	private RegisterFile rf;
	private ArrayList<String> instArray;
	private JPanel contentPane;
	private JTable registerFileTable;
	private String BaseDataAddress = "40004000";
	private String RegistersOption[];

	private String MachineCodeOption = "Binary";
	private int frequencySpeed = -1;
	private JTextPane machineCodeArea;
	private JSplitPane editTabSplitPane;
	private JTextPane inputCodeTextPane;
	private JTextPane rowLines;
	private JInternalFrame dataSegmentIF;
	private JInternalFrame textSegmentIF;
	private JTable textSegmentTable;
	private JTable dataSegmentTable;
	private JPanel buttonPanel;
	private JButton runButton;
	private JButton traceButton;
	private JButton resetButton;
	private JLabel lblPcvalue;
	private Thread ex = new Thread(new ExecutingThread());
	private AbstractButton assembleButton;
	private AbstractButton stopButton;
	private boolean terminateFlag;
	private JTabbedPane tabbedPane;
	private JEditorPane IOEditorPane;
	private JScrollPane IOEditorScrollViewPane;

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

	public void initiliseGUI() {
		// =======================================================================
		// Main Content Pane
		// =======================================================================
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		// =======================================================================

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
				if (chckbxmntmMachineCode.isSelected()) {
					editTabSplitPane.setRightComponent(machineCodeArea);
				} else {
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
				for (int i = 0; i < RegistersOption.length; i++) {
					RegistersOption[i] = aButton.getText();
				}
				
				updateRegisterFileTable(registerFileTable, rf, pc);

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

		// =======================================================================
		// South Panel For buttons
		// =======================================================================
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

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
				if (slider.getValue() == 30) {
					lblRunSpeedAt.setText("Frequency Speed, maximum");
					frequencySpeed = -1;
				} else {
					lblRunSpeedAt.setText("Frequency Speed, " + slider.getValue() + " inst/sec");
					frequencySpeed = slider.getValue();
				}
			}
		});

		assembleButton = new JButton("Assemble");

		assembleButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(assembleButton);

		runButton = new JButton("Run");
		runButton.setEnabled(false);
		runButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(runButton);

		stopButton = new JButton("Stop");

		stopButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stopButton.setEnabled(false);
		buttonPanel.add(stopButton);

		traceButton = new JButton("Trace");
		traceButton.setEnabled(false);
		traceButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(traceButton);

		resetButton = new JButton("Reset");

		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonPanel.add(resetButton);

		lblPcvalue = new JLabel("PC = 0");
		lblPcvalue.setFont(new Font("Tahoma", Font.PLAIN, 19));
		buttonPanel.add(lblPcvalue);
		// =========================================================================================

		// =========================================================================================
		// Parent Split Pane ( ChildSplitPane , RegisterFilePane)
		// =========================================================================================
		JSplitPane parentSplitPane = new JSplitPane();
		parentSplitPane.setResizeWeight(0.9);
		contentPane.add(parentSplitPane, BorderLayout.CENTER);

		// RegisterFilePane
		JScrollPane registerFilePane = new JScrollPane();
		parentSplitPane.setRightComponent(registerFilePane);
		registerFileTable = new JTable();
		registerFilePane.setViewportView(registerFileTable);
		registerFileTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		registerFileTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"R0", "R0", "0"},
				{"R1", "R1", "0"},
				{"R2", "R2", "0"},
				{"R3", "R3", "0"},
				{"R4", "R4", "0"},
				{"R5", "R5", "0"},
				{"R6", "R6", "0"},
				{"R7", "R7", "0"},
				{"R8", "R8", "0"},
				{"R9", "R9", "0"},
				{"R10", "T0", "0"},
				{"R11", "T1", "0"},
				{"R12", "T2", "0"},
				{"R13", "T3", "0"},
				{"R14", "T4", "0"},
				{"R15", "T5", "0"},
				{"R16", "T6", "0"},
				{"R17", "T7", "0"},
				{"R18", "T8", "0"},
				{"R19", "T9", "0"},
				{"R20", "S0", "0"},
				{"R21", "S1", "0"},
				{"R22", "S2", "0"},
				{"R23", "S3", "0"},
				{"R24", "S4", "0"},
				{"R25", "S5", "0"},
				{"R26", "S6", "0"},
				{"R27", "S7", "0"},
				{"R28", "S8", "0"},
				{"R29", "FP", "0"},
				{"R30", "SP", "0"},
				{"R31", "LR", "0"},
			},
			new String[] {
				"R#", "R Name", "Register Number"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		registerFileTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		registerFileTable.getColumnModel().getColumn(0).setMinWidth(50);
		registerFileTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		registerFileTable.getColumnModel().getColumn(2).setMinWidth(90);
		
		registerFileTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	if(RegistersOption[table.getSelectedRow()].equals("Decimal")) {
		        		RegistersOption[table.getSelectedRow()] = "Hex";
		        	}else if(RegistersOption[table.getSelectedRow()].equals("Hex")) {
		        		RegistersOption[table.getSelectedRow()] = "Binary";
		        	}else if(RegistersOption[table.getSelectedRow()].equals("Binary")) {
		        		RegistersOption[table.getSelectedRow()] = "Decimal";
		        	}
		            
		            updateRegisterFileTable(registerFileTable, rf, pc);
		        }
		    }
		});
		registerFileTable.setRowHeight(28);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		parentSplitPane.setLeftComponent(splitPane);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JButton clearButton = new JButton("CLEAR");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IOEditorPane.setText("");
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(clearButton, BorderLayout.SOUTH);
		
		IOEditorScrollViewPane = new JScrollPane();
		panel_1.add(IOEditorScrollViewPane);
		
		IOEditorPane = new JEditorPane();
		IOEditorScrollViewPane.setViewportView(IOEditorPane);
		IOEditorPane.setFont(new Font("Tahoma", Font.PLAIN, 22));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		splitPane.setLeftComponent(tabbedPane);

		// TabbedPane, Edit
		editTabSplitPane = new JSplitPane();
		tabbedPane.addTab("Edit", null, editTabSplitPane, null);
		editTabSplitPane.setResizeWeight(0.999);
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
				"1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n10\r\n11\r\n12\r\n13\r\n14\r\n15\r\n16\r\n17\r\n18\r\n19\r\n20\r\n21\r\n22\r\n23\r\n24\r\n25\r\n26\r\n27\r\n28\r\n29\r\n30\r\n31\r\n32\r\n33\r\n34\r\n35\r\n36\r\n37\r\n38\r\n39\r\n40\r\n41\r\n42\r\n43\r\n44\r\n45\r\n46\r\n47\r\n48\r\n49\r\n50\r\n51\r\n52\r\n53\r\n54\r\n55\r\n56\r\n57\r\n58\r\n59\r\n60\r\n61\r\n62\r\n63\r\n64\r\n65\r\n66\r\n67\r\n68\r\n69\r\n70\r\n71\r\n72\r\n73\r\n74\r\n75\r\n76\r\n77\r\n78\r\n79\r\n80\r\n81\r\n82\r\n83\r\n84\r\n85\r\n86\r\n87\r\n88\r\n89\r\n90\r\n91\r\n92\r\n93\r\n94\r\n95\r\n96\r\n97\r\n98\r\n99\r\n100\r\n101\r\n102\r\n103\r\n104\r\n105\r\n106\r\n107\r\n108\r\n109\r\n110\r\n111\r\n112\r\n113\r\n114\r\n115\r\n116\r\n117\r\n118\r\n119\r\n120\r\n121\r\n122\r\n123\r\n124\r\n125\r\n126\r\n127\r\n128\r\n129\r\n130\r\n131\r\n132\r\n133\r\n134\r\n135\r\n136\r\n137\r\n138\r\n139\r\n140\r\n141\r\n142\r\n143\r\n144\r\n145\r\n146\r\n147\r\n148\r\n149\r\n150\r\n151\r\n152\r\n153\r\n154\r\n155\r\n156\r\n157\r\n158\r\n159\r\n160\r\n161\r\n162\r\n163\r\n164\r\n165\r\n166\r\n167\r\n168\r\n169\r\n170\r\n171\r\n172\r\n173\r\n174\r\n175\r\n176\r\n177\r\n178\r\n179\r\n180\r\n181\r\n182\r\n183\r\n184\r\n185\r\n186\r\n187\r\n188\r\n189\r\n190\r\n191\r\n192\r\n193\r\n194\r\n195\r\n196\r\n197\r\n198\r\n199\r\n200\r\n201\r\n202\r\n203\r\n204\r\n205\r\n206\r\n207\r\n208\r\n209\r\n210\r\n211\r\n212\r\n213\r\n214\r\n215\r\n216\r\n217\r\n218\r\n219\r\n220\r\n221\r\n222\r\n223\r\n224\r\n225\r\n226\r\n227\r\n228\r\n229\r\n230\r\n231\r\n232\r\n233\r\n234\r\n235\r\n236\r\n237\r\n238\r\n239\r\n240\r\n241\r\n242\r\n243\r\n244\r\n245\r\n246\r\n247\r\n248\r\n249\r\n250\r\n251\r\n252\r\n253\r\n254\r\n255\r\n256\r\n257\r\n258\r\n259\r\n260\r\n261\r\n262\r\n263\r\n264\r\n265\r\n266\r\n267\r\n268\r\n269\r\n270\r\n271");
		rowLines.setFont(new Font("Segoe UI Historic", Font.PLAIN, 22));
		rowLines.setEnabled(false);
		rowLines.setEditable(false);
		editorPane.setRowHeaderView(rowLines);

		// TabbedPane, Execute ( TextSegment, DataSegment )
		JDesktopPane executeTabDesktopPane = new JDesktopPane();
		tabbedPane.addTab("Execute", null, executeTabDesktopPane, null);
		executeTabDesktopPane.setBackground(Color.WHITE);

		// TextSegment
		textSegmentIF = new JInternalFrame("Text Segment");
		textSegmentIF.setBorder(new LineBorder(new Color(0, 0, 0)));
		textSegmentIF.setMaximizable(true);
		textSegmentIF.setBounds(0, 0, 1295, 350);
		executeTabDesktopPane.add(textSegmentIF);
		textSegmentIF.setResizable(true);
		textSegmentIF.setMaximizable(true);
		Object[][] textSegmentRows = new Object[0][3];
		String[] textSegmentCols = new String[] { "Address", "Code", "Assembly Code", "Status" };
		Object[][] dataSegmentRows = new Object[100][5];
		String[] dataSegmentCols = new String[] { "Address", "+0", "+8", "+16", "+24" };
		JScrollPane scrollPane = new JScrollPane();
		textSegmentIF.getContentPane().add(scrollPane, BorderLayout.CENTER);
		textSegmentTable = new JTable();
		textSegmentTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textSegmentTable.setModel(new DefaultTableModel(textSegmentRows, textSegmentCols) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		// textSegmentTable.getColumnModel().getColumn(0).setPreferredWidth(116);
		// textSegmentTable.getColumnModel().getColumn(1).setPreferredWidth(131);
		// textSegmentTable.getColumnModel().getColumn(2).setPreferredWidth(262);
		// textSegmentTable.getColumnModel().getColumn(3).setPreferredWidth(347);
		scrollPane.setViewportView(textSegmentTable);
		textSegmentIF.setVisible(true);

		// DataSegment
		dataSegmentIF = new JInternalFrame("Data Segment");
		dataSegmentIF.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataSegmentIF.setBounds(0, 350, 1295, 350);
		executeTabDesktopPane.add(dataSegmentIF);
		dataSegmentIF.setResizable(true);
		dataSegmentIF.setMaximizable(true);

		JScrollPane dataSegmentScrollPane = new JScrollPane();
		dataSegmentIF.getContentPane().add(dataSegmentScrollPane, BorderLayout.CENTER);

		dataSegmentTable = new JTable();

		dataSegmentTable.setModel(new DefaultTableModel(dataSegmentRows, dataSegmentCols) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});

		// dataSegmentTable.getColumnModel().getColumn(0).setPreferredWidth(85);
		// dataSegmentTable.getColumnModel().getColumn(1).setPreferredWidth(73);
		// dataSegmentTable.getColumnModel().getColumn(2).setPreferredWidth(111);
		// dataSegmentTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		dataSegmentScrollPane.setViewportView(dataSegmentTable);
		dataSegmentIF.setVisible(true);

		// ==================================================================================================
	}

	public GUIInterface() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// =========================================================================================
		// Initialise the GUI Components
		// =========================================================================================
		initiliseGUI();
		// =========================================================================================

		// =========================================================================================
		// Initialise the hash map for all instructions type (ADD, J, JAL, SUB, ...)
		// =========================================================================================
		try {
			Assembler.initializeCommands();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// =========================================================================================

		// =========================================================================================
		// Initialise the program counter, registerFile and dataMemory.
		// =========================================================================================
		pc = new ProgramCounter();
		rf = new RegisterFile();
		mem = new DataMemory();
		// =========================================================================================

		
		
		RegistersOption = new String[32];
		
		for (int i = 0; i < RegistersOption.length; i++) {
			RegistersOption[i] = "Decimal";
		}
		
		// =========================================================================================
		// Reset Button
		// =========================================================================================
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.reset();
				rf.reset();
				mem.reset();
				instArray.clear();
				updateRegisterFileTable(registerFileTable, rf, pc);
				updateDataMemoryTable(dataSegmentTable, mem);
				lblPcvalue.setText("PC = 0");
				machineCodeArea.setText("");
				StyledDocument doc = (StyledDocument) rowLines.getDocument();
				Style style = rowLines.addStyle("MyHilite", null);
				StyleConstants.setBold(style, false);
				doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);

				DefaultTableModel model = (DefaultTableModel) textSegmentTable.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

			}
		});

		instArray = new ArrayList<String>();

		// =========================================================================================
		// Run Button
		// =========================================================================================
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ex = new Thread(new ExecutingThread());
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				ex.start();
			}
		});

		// =========================================================================================
		// Trace Button
		// =========================================================================================

		traceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!pc.isItRunning()) {

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
				updateRegisterFileTable(registerFileTable, rf, pc);
				updateDataMemoryTable(dataSegmentTable, mem);
				if (pc.getProgramCounter() == pc.getInstructionsList().size()) {
					pc.reset();
					rf.reset();
					mem.reset();
					instArray.clear();
				}

			}
		});

		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopButton.setEnabled(false);
				runButton.setEnabled(false);
				terminateFlag = true;
			}
		});

		assembleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IOEditorPane.setText("");
				pc.reset();
				rf.reset();
				mem.reset();
				instArray.clear();
				updateRegisterFileTable(registerFileTable, rf, pc);
				updateDataMemoryTable(dataSegmentTable, mem);
				lblPcvalue.setText("PC = 0");
				machineCodeArea.setText("");
				StyledDocument doc = (StyledDocument) rowLines.getDocument();
				Style style = rowLines.addStyle("MyHilite", null);
				StyleConstants.setBold(style, false);
				doc.setCharacterAttributes(0, rowLines.getText().toString().length() - 1, style, true);

				DefaultTableModel model = (DefaultTableModel) textSegmentTable.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				runButton.setEnabled(true);
				traceButton.setEnabled(true);
				/* Reinitialise the ProgramCounter and RegisterFile */
				pc.reset();
				rf.reset();
				mem.reset();
				machineCodeArea.setText("");

				/* Convert the text to array list of assembly instructions */

				String dataSegment = "";
				String textSegment = "";
				String inputAssemplyCode = inputCodeTextPane.getText().toString();
				if (inputAssemplyCode.toLowerCase().contains(".data")
						&& inputAssemplyCode.toLowerCase().contains(".text")) {
					if (inputAssemplyCode.indexOf(".data") < inputAssemplyCode.indexOf(".text")) {
						dataSegment = inputAssemplyCode.split(".text")[0].replace(".data\r\n", "").trim();
						textSegment = inputAssemplyCode.split(".text")[1];
					} else {
						textSegment = inputAssemplyCode.split(".data")[0].replace(".text\r\n", "").trim();
						dataSegment = inputAssemplyCode.split(".data")[1];
					}
				} else if (inputAssemplyCode.toLowerCase().contains(".data")) {
					dataSegment = inputCodeTextPane.getText().toString();
				} else {
					textSegment = inputCodeTextPane.getText().toString();
				}

				// to replace all blank lines
				String[] ab = textSegment.replaceAll("(?m)^[ \t]*\r?\n", "").split(System.getProperty("line.separator"));
				ArrayList<String> a = new ArrayList<String>();
				for (int i = 0; i < ab.length; i++) {
					a.add(ab[i]);
				}
				// ---------------------------------------------------------------------------------------------------------------

				tabbedPane.setSelectedIndex(1);
				// to save all @lables in programCounter
				for (int i = 0; i < a.size(); i++) {
					a.set(i,a.get(i).split("\\//")[0]);
					String temp = a.get(i).split(" ")[0];
					if (temp.contains("@")) {
						pc.addLableAddress(a.get(i).split(" ")[0], i);
						a.set(i, a.get(i).replace(temp, "").trim().equals("") ? null : a.get(i).replace(temp, "").trim());
						a.remove(i);
						i--;
					}
				}	
				for (int i = 0; i < a.size(); i++) {
					if (a.get(i) != null) {
						instArray.add(a.get(i));
					}
				}

				/* Convert assembly language into machine code */
				Assembler.fetchAssemblyInstruction(pc, instArray, textSegmentTable);

			}
		});

	}

	private void updateDataMemoryTable(JTable t, DataMemory MemoryS) {
		int BaseTemp = Integer.parseInt(BaseDataAddress, 16);
		for (int i = 0; i < 100; i++) {
			t.getModel().setValueAt(Integer.toString(BaseTemp, 16), i, 0);
			for (int j = 0; j < 4; j++) {
				t.getModel().setValueAt(convert8AddressesToHex(MemoryS, i * 32 + j * 8), i, j + 1);
			}
			BaseTemp = BaseTemp + 32;
		}

	}

	private String convert8AddressesToHex(DataMemory MemoryS, int Baddress) {
		String[] StringMemory = new String[8];
		long TemValue = 0;
		String DataHex = "";
		TemValue = (long) ((int) MemoryS.getData(Baddress + (7)));

		for (int i = 0; i < 7; i++) {
			TemValue = TemValue * 256 + ((int) MemoryS.getData(Baddress + (6 - i)));
		}

		return paddingToLeft(Long.toHexString(TemValue));
	}

	private String paddingToLeft(String S) {
		if (S.length() < 16) {
			int l = S.length();
			for (int i = 0; i < 16 - l; i++) {
				S = "0" + S;
			}
			return S;

		} else {
			return S.substring(0, 16);
		}
	}

	private void updateRegisterFileTable(JTable t, RegisterFile r, ProgramCounter pc) {
		for (int i = 0; i < 32; i++) {
			if (RegistersOption[i].equals("Decimal")) {
				t.getModel().setValueAt(r.getRegister(i), i, 2);
			}else if(RegistersOption[i].equals("Hex")) {
				t.getModel().setValueAt(Long.toString(r.getRegister(i), 16).toUpperCase(), i, 2);
			}else if(RegistersOption[i].equals("Binary")) {
				t.getModel().setValueAt(Long.toBinaryString(r.getRegister(i)), i, 2);
			}
		}
		
		
//		if (RegistersOption.equals("Decimal"))
//			for (int i = 0; i < 32; i++) {
//				t.getModel().setValueAt(r.getRegister(i), i, 2);
//			}
//		else if (RegistersOption.equals("Hex"))
//			for (int i = 0; i < 32; i++) {
//
//				t.getModel().setValueAt(Long.toString(r.getRegister(i), 16).toUpperCase(), i, 2);
//			}
//		else if (RegistersOption.equals("Binary"))
//			for (int i = 0; i < 32; i++) {
//				t.getModel().setValueAt(Long.toBinaryString(r.getRegister(i)), i, 2);
//			}
	}

	class ExecutingThread implements Runnable {
		private int count;

		public void terminate() {
			terminateFlag = true;
		}

		public void run() {
			assembleButton.setEnabled(false);
			count = 1;
			while (!terminateFlag && pc.getProgramCounter() < pc.getInstructionsList().size()) {
				if (pc.getProgramCounter() >= 1)
					textSegmentTable.getModel().setValueAt("", pc.getProgramCounter() - 1, 3);
				textSegmentTable.getModel().setValueAt("Executed", pc.getProgramCounter(), 3);

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
				if((pc.getInstructionsList().get(pc.getProgramCounter())).getClass().getName().equals("IInstruction")) 
					(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf, mem, IOEditorPane);
				else
					(pc.getInstructionsList().get(pc.getProgramCounter())).execute(pc, rf, mem);
				updateRegisterFileTable(registerFileTable, rf, pc);
				updateDataMemoryTable(dataSegmentTable, mem);

				// Determine the frequency speed
				if (frequencySpeed != -1 && count == frequencySpeed) {
					try {
						Thread.sleep(1000);
						count = 1;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					count++;
				}

			}
			// =================================================================================================

			terminateFlag = false;
			pc.reset();
			instArray.clear();
			assembleButton.setEnabled(true);
			stopButton.setEnabled(false);
		}

	}

}
