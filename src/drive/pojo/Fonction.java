package drive.pojo;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
}
