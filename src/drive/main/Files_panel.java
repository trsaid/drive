package drive.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Dimension;

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
		setPreferredSize(new Dimension(580, 289));

		JLabel lblTitle = new JLabel("Mes fichiers");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		add(lblTitle);
		
		JPanel panEvent = new JPanel();
		panEvent.setBackground(new Color(32,33,35));

		JPanel[] files = new JPanel[6];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				files[i] = new JPanel();
				files[i].setBounds(10 + 90*j + 10, (15 + i * (75 + 30)), this.getWidth()/7, 90);
				if((j%2) == 0) 
					files[i].setBackground(new Color(0,0,0));
				else
					files[i].setBackground(new Color(255,255,255));
				
				panEvent.add(files[i]);
			}
		}
		
        JScrollPane scroll = new JScrollPane(panEvent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setBounds(2, 2, 578, 285);
        this.add(scroll);
        
        // Taille du panel à scroll
        panEvent.setPreferredSize(new Dimension(this.getWidth(), 90*4));
        panEvent.setLayout(null);
        setVisible(true);

	}
}
