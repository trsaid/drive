package drive.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import drive.dao.dossierDAO;
import drive.pojo.Dossier;
import drive.pojo.Fichier;
import drive.pojo.Fonction;
import drive.pojo.PopClickListener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Archives_panel extends JPanel {

	private final static Archives_panel INSTANCE = new Archives_panel();

	int user_id = Main.getUser_logged().getId();
	// Taille bouton dossier
	int btnFileL = 167;
	int btnFileH = 48;
	// Espacement entre les boutons
	int espaceX = 15;
	int espaceY = 20;

	int x = 0;
	int y = 0;

	JPanel Panel_root;

	/**
	 * Create the panel.
	 */
	public Archives_panel() {
		setLayout(null);
		setBounds(10, 150, 580, 480);
		setBackground(new Color(32, 33, 35));
		setLayout(null);

		// Scroll panel

		Panel_root = new JPanel();
		Panel_root.setBorder(null);

		Panel_root.setBackground(new Color(32, 33, 35));

		JScrollPane scroll = new JScrollPane(Panel_root, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(null);
		scroll.setBounds(2, 2, 578, 480);
		this.add(scroll);

		// Taille du panel à scroll
		Panel_root.setPreferredSize(new Dimension(this.getWidth(), 90 * 5));
		Panel_root.setLayout(null);

		refreshDir();

	}

	public void ShowDossiers(ArrayList<Dossier> listDossier) {
		Panel_root.removeAll();
		Panel_root.updateUI();

		EmptyArchive();

		JLabel lblTitle = new JLabel("Archives");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		Panel_root.add(lblTitle);
		
		JLabel refresh = new JLabel("");
		refresh.setToolTipText("Actualiser");
		Fonction.IconHover(refresh, "Refresh.png", "Refresh_Hover.png");
		refresh.setBounds(523, 11, 25, 25);
		Panel_root.add(refresh);
		
		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshDir();
			}
		});

		int nbDossier = listDossier.size();

		JPanel[] files = new JPanel[nbDossier];

		// Di -> Index de la boucle unDossier.
		int Di = 0;

		for (Dossier unDossier : listDossier) {
			x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton de chaque dossier à raison de 3 par
															// ligne maximum
			y = 35 + espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton

			files[Di] = new JPanel();
			files[Di].setBounds(x, y, 167, 48);
			files[Di].setBorder(BorderFactory.createRaisedBevelBorder());
			files[Di].setLayout(null);
			files[Di].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			files[Di].setBackground(new Color(40, 40, 40));
			Panel_root.add(files[Di]);

			files[Di].addMouseListener(new PopClickListener(unDossier));

			files[Di].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ShowFichiers(unDossier);
				}
			});

			JLabel folder_icon = new JLabel("");
			folder_icon.setBounds(12, 0, 24, 48);
			files[Di].add(folder_icon);

			// Changer d'icone quand la souris est sur le panel
			Fonction.IconHover(folder_icon, "dossier.png", "dossier_hover.png", files[Di]);

			JLabel file_name = new JLabel();
			file_name.setBounds(40, 0, 100, 48);
			file_name.setFont(new Font("Tahoma", Font.PLAIN, 12));
			file_name.setForeground(new Color(255, 255, 255));
			file_name.setText(unDossier.getNom());
			files[Di].add(file_name);

			Di++;

		}
	}

	public void ShowFichiers(Dossier dossier) {

		// Di -> Index de la boucle unDossier.
		int Di = 0;

		Panel_root.removeAll();
		Panel_root.updateUI();

		JLabel lblTitle = new JLabel(dossier.getNom());
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		Panel_root.add(lblTitle);

		ArrayList<Fichier> listFichiers;
		dossier.setFileList(dossierDAO.getInstance().fichierArchive(dossier.getId()));
		listFichiers = dossier.getFileList();
		int nbFichiers = listFichiers.size();

		JPanel[] Fichiers_pan = new JPanel[nbFichiers];

		JLabel label = new JLabel("");
		Fonction.IconHover(label, "icons8_Left_25px.png", "icons8_Left_25px_hover.png");
		label.setBounds(10, 11, 25, 25);
		Panel_root.add(label);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshDir();
			}
		});
		for (Fichier unFichier : listFichiers) {
			x = espaceX + (btnFileL + espaceX) * (Di % 3); // Position X du bouton
			y = 35 + espaceX + (btnFileH + espaceY) * (Di / 3); // Position Y du bouton

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

	public void EmptyArchive() {
		ArrayList<Dossier> listDossier = dossierDAO.getInstance().listArchive(user_id);
		if (listDossier.isEmpty()) {
			JLabel txt = new JLabel("Vous n'avez aucun dossier archivé.");
			txt.setHorizontalAlignment(SwingConstants.CENTER);
			txt.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txt.setForeground(new Color(255, 255, 255));
			txt.setBounds(0, 11, 578, 480);
			Panel_root.add(txt);
		}

	}
	
	public void refreshDir() {
		ArrayList<Dossier> listArchive;
		listArchive = dossierDAO.getInstance().listArchive(user_id);

		ShowDossiers(listArchive);
	}

	public static Archives_panel getInstance() {
		return INSTANCE;
	}
}
