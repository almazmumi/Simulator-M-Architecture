import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUIInterface extends JFrame {
	private ProgramCounter pc;
	private RegisterFile rf;
	private ArrayList<String> instArray;
	private JPanel contentPane;
	private JTable table;
	private int count; // for btnCompileLineByLine

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
		setBounds(100, 100, 894, 1042);

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTextPane txtpnCodeview = new JTextPane();
		txtpnCodeview.setFont(new Font("Tahoma", Font.PLAIN, 22));
		contentPane.add(txtpnCodeview, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		JButton btnCompileAll = new JButton("Compile All");
		btnCompileAll.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(btnCompileAll);
		JButton btnCompileOneLine = new JButton("Compile Line by Line");
		btnCompileOneLine.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(btnCompileOneLine);
		Document d = txtpnCodeview.getDocument();

		// To change the instruction name style
		d.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
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
							StyledDocument doc = (StyledDocument) txtpnCodeview.getDocument();
							int start = 0;
							for (int i = 0; i < lines.length - 1; i++) {
								start += lines[i].length() + 1;
							}

							int end = start + lines[lines.length - 1].split(" ")[0].length() + 1;

							StyledDocument docc = txtpnCodeview.getStyledDocument();

							Style style = txtpnCodeview.addStyle("MyHilite", null);

							// style = textPane.getStyle("MyHilite");
							StyleConstants.setBold(style, true);
							doc.setCharacterAttributes(start, end - start, style, false);
						}
					};
					SwingUtilities.invokeLater(doAssist);

				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

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

		instArray = new ArrayList<String>();

		btnCompileAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(count);
				if (count == -1) {
					// Convert the text to array list of instructions -----------------------
					String[] a = txtpnCodeview.getText().split(System.getProperty("line.separator"));
					for (int i = 0; i < a.length; i++) {
						if (!a[i].equals(""))
							instArray.add(a[i]);
					}
					// -----------------------------------------------------------------------

					// Initialise the hash map for all instructions type (ADD, J, JAL, SUB, ...)
					try {
						Assembler.initializeCommands();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pc = new ProgramCounter();
					rf = new RegisterFile();

					// Convert assembly language into machine code -------------------------
					Assembler.SetInstructionInPC(pc, instArray);

					count = 0;
					// Execute the instruction list
					for (int i = count; i < pc.getInstructionsList().size(); i++) {
						if (pc.getInstructionsList().get(i).getClass().getName().equals("IInstruction"))
							((IInstruction) pc.getInstructionsList().get(i)).execute(rf);
						else if (pc.getInstructionsList().get(i).getClass().getName().equals("RInstruction"))
							((RInstruction) pc.getInstructionsList().get(i)).execute(rf);
					}

					updateRegisterFileTable(table, rf, pc);
					count = -1;
				} else {
					System.out.println("No Way" + count);
					// Execute the instruction list
					for (int i = count; i < pc.getInstructionsList().size(); i++) {
						if (pc.getInstructionsList().get(i).getClass().getName().equals("IInstruction"))
							((IInstruction) pc.getInstructionsList().get(i)).execute(rf);
						else if (pc.getInstructionsList().get(i).getClass().getName().equals("RInstruction"))
							((RInstruction) pc.getInstructionsList().get(i)).execute(rf);
					}

					updateRegisterFileTable(table, rf, pc);
					count = -1;
				}

			}
		});

		count = -1;

		btnCompileOneLine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(count);
				
				if (count == -1) {
					// Convert the text to array list of instructions -----------------------
					String[] a = txtpnCodeview.getText().split(System.getProperty("line.separator"));
					for (int i = 0; i < a.length; i++) {
						if (!a[i].equals(""))
							instArray.add(a[i]);
					}
					// -----------------------------------------------------------------------

					// Initialise the hash map for all instructions type (ADD, J, JAL, SUB, ...)
					try {
						Assembler.initializeCommands();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pc = new ProgramCounter();
					rf = new RegisterFile();

					// Convert assembly language into machine code -------------------------
					Assembler.SetInstructionInPC(pc, instArray);
					
					count = 0;

					// Execute the instruction list

					if (pc.getInstructionsList().get(count).getClass().getName().equals("IInstruction"))
						((IInstruction) pc.getInstructionsList().get(count)).execute(rf);
					else if (pc.getInstructionsList().get(count).getClass().getName().equals("RInstruction"))
						((RInstruction) pc.getInstructionsList().get(count)).execute(rf);

					updateRegisterFileTable(table, rf, pc);
					if (count == pc.getInstructionsList().size() - 1) {
						count = -1;
					} else {
						count++;
					}
					
				} else {

					// Execute the instruction list
					if (pc.getInstructionsList().get(count).getClass().getName().equals("IInstruction"))
						((IInstruction) pc.getInstructionsList().get(count)).execute(rf);
					else if (pc.getInstructionsList().get(count).getClass().getName().equals("RInstruction"))
						((RInstruction) pc.getInstructionsList().get(count)).execute(rf);

					updateRegisterFileTable(table, rf, pc);
					if (count == pc.getInstructionsList().size() - 1) {
						count = -1;
					} else {
						count++;
					}
					System.out.println(count);
				}

			}
		});
	}

	private void updateRegisterFileTable(JTable t, RegisterFile r, ProgramCounter pc) {
		for (int i = 0; i < 32; i++) {
			t.getModel().setValueAt(r.getRegister(i), i, 2);
		}
	}
}
