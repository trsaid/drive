package drive.main;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.JLabel;

public class Files_panel extends JPanel {

	/**
	 * Create the panel.
	 */
	public Files_panel() {
		setLayout(null);
		
		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), null));
		setBounds(10, 150, 580, 289);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JLabel lblTest = new JLabel("Test");
		lblTest.setForeground(new Color(255, 255, 255));
		lblTest.setBounds(188, 170, 46, 14);
		add(lblTest);

	}
}
