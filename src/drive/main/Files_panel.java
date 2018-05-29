package drive.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import org.apache.commons.net.ftp.FTPClient;

import drive.dao.dossierDAO;
import drive.dao.loginDAO;
import drive.dao.uploadDAO;
import drive.pojo.Dossier;
import drive.pojo.FTPUpload;
import drive.pojo.Fichier;
import drive.pojo.Fonction;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

public class Files_panel extends JPanel {

	int user_id = loginDAO.membre.getId();
	// Taille bouton dossier
	int btnFileL = 167;
	int btnFileH = 48;
	// Espacement entre les boutons
	int espaceX = 15;
	int espaceY = 20;

	int x = 0;
	int y = 0;

	public Files_panel() {

		setLayout(null);

		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), null));
		setBounds(10, 150, 580, 289);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		setPreferredSize(new Dimension(580, 289));

		// Scroll panel

		JPanel Panel_root = new JPanel();
		Panel_root.setBackground(new Color(32, 33, 35));

		JScrollPane scroll = new JScrollPane(Panel_root, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(null);
		scroll.setBounds(2, 2, 578, 285);
		this.add(scroll);

		// Taille du panel à scroll
		Panel_root.setPreferredSize(new Dimension(this.getWidth(), 90 * 4));
		Panel_root.setLayout(null);

		JLabel lblTitle = new JLabel("Mes dossiers");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		Panel_root.add(lblTitle);

		List<Dossier> listDossier;
		listDossier = dossierDAO.getInstance().listDossier(user_id);

		ShowDossiers(Panel_root, listDossier);

	}

	public void ShowDossiers(JPanel sourcePanel, List<Dossier> listDossier) {
		sourcePanel.removeAll();
		sourcePanel.updateUI();

		JLabel label = new JLabel("");
		label.setToolTipText("Cr\u00E9er un nouveau dossier");
		Fonction.IconHover(label, "Plus.png", "Plus_Hover.png");
		label.setBounds(523, 11, 25, 25);
		sourcePanel.add(label);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FTPClient ftpClient = FTPUpload.loginFTP();
				String nom_dossier = Fonction.Custom_TF_Dialog("Nom du dossier :");
				FTPUpload.addDossier(nom_dossier, ftpClient);

				if (nom_dossier == null || nom_dossier.isEmpty()) {
					 JOptionPane.showMessageDialog(Main.getMainFrame(), "Veuillez enter le nom du dossier.", "Création du fichier impossible !", JOptionPane.INFORMATION_MESSAGE);
				}else if (dossierDAO.getInstance().folderExist(nom_dossier)) {
					JOptionPane.showMessageDialog(Main.getMainFrame(), "Dossier déjà éxistant.", "Création du fichier impossible !", JOptionPane.INFORMATION_MESSAGE);
				}else {
					dossierDAO.getInstance().addFolder(nom_dossier);
					sourcePanel.removeAll();
					List<Dossier> newListDossier = dossierDAO.getInstance().listDossier(user_id);
					ShowDossiers(sourcePanel, newListDossier);
					sourcePanel.updateUI();
				}
			}
		});

		int nbDossier = listDossier.size();

		JPanel[] files = new JPanel[nbDossier];

		// Di -> Index de la boucle unDossier.
		int Di = 0;

		for (Dossier unDossier : listDossier) {
			x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton de chaque dossier à raison de 3 par
															// ligne maximum
			y = 25 + espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton

			files[Di] = new JPanel();
			files[Di].setBounds(x, y, 167, 48);
			files[Di].setBorder(BorderFactory.createRaisedBevelBorder());
			files[Di].setLayout(null);
			files[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			files[Di].setBackground(new Color(40, 40, 40));
			sourcePanel.add(files[Di]);

			files[Di].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ShowFichiers(sourcePanel, unDossier);
				}
			});

			JLabel folder_icon = new JLabel("");
			Fonction.IconHover(folder_icon, "dossier.png", "dossier_hover.png");
			folder_icon.setBounds(12, 0, 24, 48);
			files[Di].add(folder_icon);

			JLabel file_name = new JLabel();
			file_name.setBounds(40, 0, 100, 48);
			file_name.setFont(new Font("Tahoma", Font.PLAIN, 12));
			file_name.setForeground(new Color(255, 255, 255));
			file_name.setText(unDossier.getNom());
			// file_name.setToolTipText(unDossier.getNom());
			files[Di].add(file_name);

			Di++;

		}
	}

	public void ShowFichiers(JPanel sourcePanel, Dossier dossier) {

		JLabel lblTitle = new JLabel(dossier.getNom());
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		sourcePanel.add(lblTitle);

		// Di -> Index de la boucle unDossier.
		int Di = 0;
		x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton
		y = 25 + espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton
		sourcePanel.removeAll();
		sourcePanel.updateUI();

		ArrayList<Fichier> listFichiers;
		dossier.setFileList(dossierDAO.getInstance().listFichier(dossier.getId()));
		listFichiers = dossier.getFileList();
		int nbFichiers = listFichiers.size();

		JPanel[] Fichiers_pan = new JPanel[nbFichiers];

		JLabel label = new JLabel("");
		Fonction.IconHover(label, "icons8_Left_25px.png", "icons8_Left_25px_hover.png");
		label.setBounds(10, 11, 25, 25);
		sourcePanel.add(label);
		setVisible(true);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Dossier> listDossier;
				listDossier = dossierDAO.getInstance().listDossier(user_id);
				ShowDossiers(sourcePanel, listDossier);
			}
		});
		for (Fichier unFichier : listFichiers) {

			Fichiers_pan[Di] = new JPanel();
			Fichiers_pan[Di].setBounds(x, y, 167, 48);
			Fichiers_pan[Di].setBorder(BorderFactory.createRaisedBevelBorder());
			Fichiers_pan[Di].setLayout(null);
			Fichiers_pan[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Fichiers_pan[Di].setBackground(new Color(40, 40, 40));
			sourcePanel.add(Fichiers_pan[Di]);

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
}
