package drive.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import drive.dao.dossierDAO;
import drive.dao.loginDAO;
import drive.pojo.Membre;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

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
		panEvent.setBackground(new Color(32, 33, 35));
		
		int user_id = loginDAO.membre.getId();

		List<String> listDossier;
		try {
			listDossier = dossierDAO.getInstance().listDossier(user_id);
			int nbDossier = listDossier.size();
			
			JPanel[] files = new JPanel[nbDossier];
			
			// Di -> Index de la boucle unDossier.
			int Di = 0;
			// Taille bouton dossier
			int btnFileL = 167;
			int btnFileH = 48;
			// Espacement entre les boutons
			int espaceX = 15;
			int espaceY = 20;
			
			for(String unDossier : listDossier) {
				int x = espaceX + (btnFileL + espaceX) *(Di%3); // Position X du bouton de chaque dossier  à raison de 3 par ligne maximum
				int y = espaceX + (btnFileH + espaceY) * (Di/3); // Position Y du bouton
				
				files[Di] = new JPanel();
				files[Di].setBounds(x, y, 167, 48);
				files[Di].setBorder(BorderFactory.createRaisedBevelBorder());
				files[Di].setLayout(null);
				files[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				files[Di].setBackground(new Color(40, 40, 40));
				panEvent.add(files[Di]);
				
				final Integer innerDi = new Integer(Di);
				
				files[Di].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						files[innerDi].setBackground(new Color(113, 142, 222));

					}
				});
				
				JLabel folder_icon = new JLabel("");
				folder_icon.setIcon(new ImageIcon(home.class.getResource("/images/dossier.png")));
				folder_icon.setBounds(12, 0, 24, 48);
				files[Di].add(folder_icon);
				
				JLabel file_name = new JLabel();
				file_name.setBounds(40, 0, 100, 48);
				file_name.setFont(new Font("Tahoma", Font.PLAIN, 12));
				file_name.setForeground(new Color(255, 255, 255));
				file_name.setText(unDossier);
				files[Di].add(file_name);
				
				Di++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JScrollPane scroll = new JScrollPane(panEvent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(null);
		scroll.setBounds(2, 2, 578, 285);
		this.add(scroll);

		// Taille du panel à scroll
		panEvent.setPreferredSize(new Dimension(this.getWidth(), 90 * 4));
		panEvent.setLayout(null);
		setVisible(true);

	}
}
