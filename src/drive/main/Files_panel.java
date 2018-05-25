package drive.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import drive.dao.dossierDAO;
import drive.dao.loginDAO;
import drive.pojo.Dossier;
import drive.pojo.Fichier;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

		JPanel Panel_root = new JPanel();
		Panel_root.setBackground(new Color(32, 33, 35));

		int user_id = loginDAO.membre.getId();

		List<Dossier> listDossier;
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

			int x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton de chaque dossier à raison de 3
																// par ligne maximum
			int y = espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton
			for (Dossier unDossier : listDossier) {

				files[Di] = new JPanel();
				files[Di].setBounds(x, y, 167, 48);
				files[Di].setBorder(BorderFactory.createRaisedBevelBorder());
				files[Di].setLayout(null);
				files[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				files[Di].setBackground(new Color(40, 40, 40));
				Panel_root.add(files[Di]);

				files[Di].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						Panel_root.removeAll();
						Panel_root.updateUI();

						ArrayList<Fichier> listFichiers;
						unDossier.setFileList(dossierDAO.getInstance().listFichier(unDossier.getId()));
						listFichiers = unDossier.getFileList();
						int nbFichiers = listFichiers.size();

						JPanel[] Fichiers_pan = new JPanel[nbFichiers];

						int Di = 0;

						int x = 0;
						int y = 0;

						for (Fichier unFichier : listFichiers) {

							x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton
							y = espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton

							Fichiers_pan[Di] = new JPanel();
							Fichiers_pan[Di].setBounds(x, y, 167, 48);
							Fichiers_pan[Di].setBorder(BorderFactory.createRaisedBevelBorder());
							Fichiers_pan[Di].setLayout(null);
							Fichiers_pan[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							Fichiers_pan[Di].setBackground(new Color(40, 40, 40));
							Panel_root.add(Fichiers_pan[Di]);

							JLabel file_name = new JLabel();
							file_name.setBounds(40, 0, 100, 48);
							file_name.setFont(new Font("Tahoma", Font.PLAIN, 12));
							file_name.setForeground(new Color(255, 255, 255));
							file_name.setText(unFichier.getNom());
							file_name.setToolTipText(unFichier.getNom());
							Fichiers_pan[Di].add(file_name);

							Di++;

						}

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
				file_name.setText(unDossier.getNom());
				file_name.setToolTipText(unDossier.getNom());
				files[Di].add(file_name);

				Di++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JScrollPane scroll = new JScrollPane(Panel_root, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(null);
		scroll.setBounds(2, 2, 578, 285);
		this.add(scroll);

		// Taille du panel à scroll
		Panel_root.setPreferredSize(new Dimension(this.getWidth(), 90 * 4));
		Panel_root.setLayout(null);

		JLabel label = new JLabel("");
		label.setToolTipText("Cr\u00E9er un nouveau dossier");
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label.setIcon(new ImageIcon(Files_panel.class.getResource("/images/Plus_Hover.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setIcon(new ImageIcon(Files_panel.class.getResource("/images/Plus.png")));
			}
		});
		label.setIcon(new ImageIcon(Files_panel.class.getResource("/images/Plus.png")));
		label.setBounds(523, 11, 25, 25);
		Panel_root.add(label);
		setVisible(true);

	}
}
