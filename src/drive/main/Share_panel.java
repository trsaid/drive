package drive.main;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Share_panel extends JPanel {

	/**
	 * Create the panel.
	 */
	public Share_panel() {
		setLayout(null);
		
		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), null));
		setBounds(10, 150, 580, 289);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Partagé avec moi");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(208, 11, 164, 25);
		add(lblTitle);

	}
}
