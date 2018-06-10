package drive.pojo;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import drive.main.Files_panel;
import drive.main.Main;

public class Fonction {
	public static String PassEncrypt(String pass) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = null;
		try {
			hash = digest.digest(pass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String password = DatatypeConverter.printHexBinary(hash);

		return password;

	}

	public static void IconHover(JLabel source, String normal, String Hover) {
		source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + normal)));
		source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		source.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + Hover)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + normal)));
			}
		});
	}

	public static void IconHover(JLabel source, String normal, String Hover, JPanel panelsource) {
		source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + normal)));
		source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panelsource.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + Hover)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				source.setIcon(new ImageIcon(Files_panel.class.getResource("/images/" + normal)));
			}
		});
	}

	public static String Custom_TF_Dialog(String Msg) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(Msg);
		JTextField txt = new JTextField(25);
		panel.add(label);
		panel.add(txt);

		String[] options = new String[] { "Valider", "Annuler" };
		int option = JOptionPane.showOptionDialog(Main.getMainFrame(), panel, "Confirmation", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 0) {
			return txt.getText();
		} else {
			return null;
		}
	}
	public static String renameDialog(String name) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Entrez le nouveau nom :");
		JTextField txt = new JTextField(25);
		txt.setText(name);
		panel.add(label);
		panel.add(txt);

		String[] options = new String[] { "Valider", "Annuler" };
		int option = JOptionPane.showOptionDialog(Main.getMainFrame(), panel, "Renommer un dossier", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 0) {
			return txt.getText();
		} else {
			return null;
		}
	}
	public static String shareDialog() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Adresse E-mail de l'utilisateur :");
		JTextField txt = new JTextField(25);
		panel.add(label);
		panel.add(txt);

		String[] options = new String[] { "Valider", "Annuler" };
		int option = JOptionPane.showOptionDialog(Main.getMainFrame(), panel, "Partager un dossier", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 0) {
			return txt.getText();
		} else {
			return null;
		}
	}

	public static void dirCreat(File dir) {
		if (!dir.exists()) {
			System.out.println("creating directory: " + dir.getName());
			boolean result = false;

			try {
				dir.mkdir();
				result = true;
			} catch (SecurityException se) {
				JOptionPane.showMessageDialog(null, "Erreur lors de la création du dossier : " + se.getMessage());
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}
}
