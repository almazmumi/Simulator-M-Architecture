import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Window extends JFrame {
	private ArrayList<String> instArray;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 906, 843);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel T = new JLabel(
				"<html>\r\n\r\nR0 = 0 <br>\r\nR1 = 0 <br>\r\nR2 = 0 <br>\r\nR3 = 0 <br>\r\nR4 = 0 <br>\r\nR5 = 0 <br>\r\nR6 = 0 <br>\r\nR7 = 0 <br>\r\nR8 = 0 <br>\r\nR9 = 0 <br>\r\nR10 = 0 <br>\r\nR11 = 0 <br>\r\nR12 = 0 <br>\r\nR13 = 0<br>\r\nR14 = 0<br>\r\nR15 = 0<br>\r\nR16 = 0<br>\r\nR17 = 0<br>\r\nR18 = 0<br>\r\nR19 = 0<br>\r\nR20 = 0<br>\r\nR21 = 0<br>\r\nR22 = 0<br>\r\nR23 = 0<br>\r\nR24 = 0<br>\r\nR25 = 0<br>\r\nR26 = 0<br>\r\nR27 = 0<br>\r\nR28 = 0<br>\r\nR29 = 0<br>\r\nR30 = 0<br>\r\nR31 = 0\r\n\r\n</html>");
		T.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(T, BorderLayout.EAST);

		JButton btnCombile = new JButton("Combile");
		btnCombile.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(btnCombile, BorderLayout.SOUTH);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(textPane, BorderLayout.CENTER);

		instArray = new ArrayList<String>();
		btnCombile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] a = textPane.getText().split(System.getProperty("line.separator"));
				for (int i = 0; i < a.length; i++) {
					if (!a[i].equals(""))
						instArray.add(a[i]);
				}
				try {
					AssemblerNotUsed.loadAllInstructions();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				ProgramCounter pc = new ProgramCounter();
				RegisterFile r = new RegisterFile();

				AssemblerNotUsed.fetchInstruction(r, pc, instArray);

				for (int i = 0; i < pc.getInstructionsList().size(); i++) {
					((IInstruction) pc.getInstructionsList().get(i)).execute(r);
				}

				T.setText(r.toString());
			}
		});

	}

}
