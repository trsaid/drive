package drive.main;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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
		
		JLabel lblTitle = new JLabel("Mes fichiers");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		add(lblTitle);

	}
}
