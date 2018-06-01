package drive.main;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import drive.pojo.PopClickListener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

public class Files_panel extends JPanel {

	private final static Files_panel INSTANCE = new Files_panel();

	static int user_id = Main.getUser_logged().getId();
	// Taille bouton dossier
	static int btnFileL = 167;
	static int btnFileH = 48;
	// Espacement entre les boutons
	static int espaceX = 15;
	static int espaceY = 20;

	static int x = 0;
	static int y = 0;

	static JPanel Panel_root;

	public Files_panel() {
		setBorder(null);

		setLayout(null);
		setBounds(10, 150, 580, 480);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		setPreferredSize(new Dimension(580, 480));

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
		Panel_root.setPreferredSize(new Dimension(this.getWidth(), 90 * 4));
		Panel_root.setLayout(null);

		List<Dossier> listDossier;
		listDossier = dossierDAO.getInstance().listDossier(user_id);

		ShowDossiers(listDossier);

	}

	public void ShowDossiers(List<Dossier> listDossier) {
		Panel_root.removeAll();
		Panel_root.updateUI();
		EmptyCloud();

		JLabel lblTitle = new JLabel("Mes dossiers");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		Panel_root.add(lblTitle);

		JLabel label = new JLabel("");
		label.setToolTipText("Cr\u00E9er un nouveau dossier");
		Fonction.IconHover(label, "Plus.png", "Plus_Hover.png");
		label.setBounds(523, 11, 25, 25);
		Panel_root.add(label);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FTPClient ftpClient = FTPUpload.loginFTP();
				String nom_dossier = Fonction.Custom_TF_Dialog("Nom du dossier :");
				FTPUpload.addDossier(nom_dossier, ftpClient);

				if (nom_dossier == null || nom_dossier.isEmpty()) {
					JOptionPane.showMessageDialog(Main.getMainFrame(), "Veuillez enter le nom du dossier.",
							"Création du fichier impossible !", JOptionPane.INFORMATION_MESSAGE);
				} else if (dossierDAO.getInstance().folderExist(nom_dossier)) {
					JOptionPane.showMessageDialog(Main.getMainFrame(), "Dossier déjà éxistant.",
							"Création du fichier impossible !", JOptionPane.INFORMATION_MESSAGE);
				} else {
					dossierDAO.getInstance().addFolder(nom_dossier);
					Panel_root.removeAll();
					List<Dossier> newListDossier = dossierDAO.getInstance().listDossier(user_id);
					ShowDossiers(newListDossier);
					Panel_root.updateUI();
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
			// file_name.setToolTipText(unDossier.getNom());
			files[Di].add(file_name);

			Di++;

		}
	}

	public void ShowFichiers(Dossier dossier) {

		// Di -> Index de la boucle unDossier.
		int Di = 0;

		Panel_root.removeAll();
		Panel_root.updateUI();

		modifyLabel(dossier, Panel_root);

		JLabel lblTitle = new JLabel(dossier.getNom());
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(224, 11, 132, 25);
		Panel_root.add(lblTitle);

		ArrayList<Fichier> listFichiers;
		dossier.setFileList(dossierDAO.getInstance().listFichier(dossier.getId()));
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
				List<Dossier> listDossier;
				listDossier = dossierDAO.getInstance().listDossier(user_id);
				ShowDossiers(listDossier);
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

	public void modifyLabel(Dossier dossier, JPanel sourcePanel) {
		TransferHandler th = new TransferHandler() {

			@Override
			public boolean canImport(JComponent comp, DataFlavor[] tf) {
				return true;
			};

			@SuppressWarnings("unchecked")
			@Override
			public boolean importData(JComponent comp, Transferable t) {
				List<File> files;
				try {
					files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
					// upload_file_size = files.size();
					// lbl_upload_txt.setText("Vous avez envoyé " + upload_file_size + " fichier" +
					// (upload_file_size > 1 ? "s." : "."));

					FTPUpload U = new FTPUpload(files, dossier);
					U.start();
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		};
		setTransferHandler(th);
	}

	public void EmptyCloud() {
		ArrayList<Dossier> listDossier = dossierDAO.getInstance().listDossier(user_id);
		if (listDossier.isEmpty()) {
			JLabel txt = new JLabel("Vous n'avez toujours pas créé de dossier");
			txt.setHorizontalAlignment(SwingConstants.CENTER);
			txt.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txt.setForeground(new Color(255, 255, 255));
			txt.setBounds(0, 11, 578, 480);
			Panel_root.add(txt);
		}
		
	}

	public void EmptyFolders() {

	}

	public static Files_panel getInstance() {
		return INSTANCE;
	}
}
