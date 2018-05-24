package drive.main;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentListener;
import javax.xml.bind.DatatypeConverter;

import drive.dao.membreDAO;
import drive.pojo.Fonction;
import drive.pojo.Membre;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.beans.PropertyChangeEvent;

public class Account_panel extends JPanel {

	private JTextField TF_Email, TF_Lname, TF_Fname;
	private JPasswordField PF_pass, PF_passConf;

	private JLabel icon_1, icon_2, icon_3, icon_4, icon_5;
	private JLabel lbl_error_msg;

	// =================================

	/**
	 * Create the panel.
	 */
	public Account_panel() {

		// On stock le membre
		Membre membre = Main.getUser_logged();

		setBorder(null);
		setBounds(10, 150, 580, 491);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		setLayout(null);

		// =================================

		JLabel lblTitle = new JLabel("Mon compte");
		lblTitle.setBounds(224, 11, 132, 25);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		add(lblTitle);

		// =================================

		JLabel lbl_email = new JLabel("Adresse E-mail");
		lbl_email.setForeground(Color.WHITE);
		lbl_email.setBounds(10, 65, 92, 14);
		add(lbl_email);

		// =================================

		JLabel lbl_name = new JLabel("Nom");
		lbl_name.setForeground(Color.WHITE);
		lbl_name.setBounds(10, 145, 92, 14);
		add(lbl_name);

		// =================================

		JLabel lbl_mdp = new JLabel("Mot de passe");
		lbl_mdp.setForeground(Color.WHITE);
		lbl_mdp.setBounds(370, 65, 120, 14);
		add(lbl_mdp);

		// =================================

		JLabel lbl_mdp_confirm = new JLabel("Confirmation du mot de passe");
		lbl_mdp_confirm.setForeground(Color.WHITE);
		lbl_mdp_confirm.setBounds(370, 145, 200, 14);
		add(lbl_mdp_confirm);

		// =================================

		JLabel lbl_prenom = new JLabel("Pr\u00E9nom");
		lbl_prenom.setForeground(Color.WHITE);
		lbl_prenom.setBounds(10, 225, 92, 14);
		add(lbl_prenom);

		// =================================

		JLabel icons[] = { icon_1 = new JLabel(""), icon_2 = new JLabel(""), icon_3 = new JLabel(""),
				icon_4 = new JLabel(""), icon_5 = new JLabel("") };
		JTextField textFields[] = { TF_Email = new JTextField(), TF_Lname = new JTextField(),
				TF_Fname = new JTextField(), PF_pass = new JPasswordField(), PF_passConf = new JPasswordField() };

		for (int i = 0; i < textFields.length; i++) {

			BorderNormal(textFields[i], icons[i]);

			final Integer innerI = new Integer(i);
			textFields[i].addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					BorderSelected(textFields[innerI], icons[innerI]);
				}

				@Override
				public void focusLost(FocusEvent e) {
					BorderNormal(textFields[innerI], icons[innerI]);
				}
			});
		}

		icon_1.setHorizontalAlignment(SwingConstants.CENTER);
		icon_1.setHorizontalTextPosition(SwingConstants.CENTER);
		icon_1.setIcon(new ImageIcon(Main.class.getResource("/images/Email.png")));
		icon_1.setBounds(10, 90, 42, 40);
		add(icon_1);

		TF_Email.setFont(new Font("Tahoma", Font.PLAIN, 17));
		TF_Email.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		TF_Email.setEnabled(false);
		TF_Email.setText(membre.getEmail());
		TF_Email.setForeground(Color.WHITE);
		TF_Email.setColumns(10);
		TF_Email.setCaretColor(Color.WHITE);
		TF_Email.setBackground(new Color(32, 33, 35));
		TF_Email.setBounds(50, 90, 200, 40);
		add(TF_Email);

		// =================================

		icon_2.setIcon(new ImageIcon(Account_panel.class.getResource("/images/User_Groups.png")));
		icon_2.setHorizontalTextPosition(SwingConstants.CENTER);
		icon_2.setHorizontalAlignment(SwingConstants.CENTER);
		icon_2.setBounds(10, 170, 42, 40);
		add(icon_2);

		TF_Lname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		TF_Lname.setText(membre.getLName());
		TF_Lname.setForeground(Color.WHITE);
		TF_Lname.setColumns(10);
		TF_Lname.setCaretColor(Color.WHITE);
		TF_Lname.setBackground(new Color(32, 33, 35));
		TF_Lname.setBounds(50, 170, 200, 40);
		add(TF_Lname);

		// =================================

		icon_3.setIcon(new ImageIcon(Account_panel.class.getResource("/images/User.png")));
		icon_3.setHorizontalTextPosition(SwingConstants.CENTER);
		icon_3.setHorizontalAlignment(SwingConstants.CENTER);
		icon_3.setBounds(10, 250, 42, 40);
		add(icon_3);

		TF_Fname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		TF_Fname.setText(membre.getFName());
		TF_Fname.setForeground(Color.WHITE);
		TF_Fname.setColumns(10);
		TF_Fname.setCaretColor(Color.WHITE);
		TF_Fname.setBackground(new Color(32, 33, 35));
		TF_Fname.setBounds(50, 250, 200, 40);
		add(TF_Fname);

		// =================================

		icon_4.setIcon(new ImageIcon(Account_panel.class.getResource("/images/password.png")));
		icon_4.setHorizontalTextPosition(SwingConstants.CENTER);
		icon_4.setHorizontalAlignment(SwingConstants.CENTER);
		icon_4.setBounds(330, 90, 42, 40);
		add(icon_4);

		PF_pass.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PF_pass.setForeground(Color.WHITE);
		PF_pass.setColumns(10);
		PF_pass.setCaretColor(Color.WHITE);
		PF_pass.setBackground(new Color(32, 33, 35));
		PF_pass.setBounds(370, 90, 200, 40);
		add(PF_pass);

		// =================================

		icon_5.setIcon(new ImageIcon(Account_panel.class.getResource("/images/password.png")));
		icon_5.setHorizontalTextPosition(SwingConstants.CENTER);
		icon_5.setHorizontalAlignment(SwingConstants.CENTER);
		icon_5.setBounds(330, 170, 42, 40);
		add(icon_5);

		PF_passConf.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PF_passConf.setForeground(Color.WHITE);
		PF_passConf.setColumns(10);
		PF_passConf.setCaretColor(Color.WHITE);
		PF_passConf.setBackground(new Color(32, 33, 35));
		PF_passConf.setBounds(370, 170, 200, 40);
		add(PF_passConf);

		// =================================

		lbl_error_msg = new JLabel("");
		lbl_error_msg.setVerticalAlignment(SwingConstants.TOP);
		lbl_error_msg.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_error_msg.setForeground(Color.RED);
		lbl_error_msg.setBounds(10, 362, 560, 118);
		add(lbl_error_msg);

		JButton btn_submit = new JButton("Valider les modifications");
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Lname = TF_Lname.getText();
				String Fname = TF_Fname.getText();
				String pass = PF_pass.getText();
				String pass2 = PF_passConf.getText();

				ArrayList<String> error_msg = new ArrayList<String>();
				String error_msg_final = "";

				// Conversion du MDP en SHA256
				String DialogPW = Fonction.PassEncrypt(PasswordDialog());

				if (membre.getPassword().equals(DialogPW)) {

					//Verification nom
					if (Lname.equals("")) {
						BorderError(TF_Lname, icon_2);
						error_msg.add("- Veuillez entrer votre nom.");

					} else if (!membre.getLName().equals(Lname)) {
						error_msg.add("- Votre nom a été modifier.");
						membreDAO.getInstance().UpdateLname(membre, Lname);
					}
					//Verification Prénom
					if (Fname.equals("")) {
						BorderError(TF_Fname, icon_3);
						error_msg.add("- Veuillez entrer votre prénom.");
					} else if (!membre.getFName().equals(Fname)) {
						error_msg.add("- Votre nom a été modifier.");
						membreDAO.getInstance().UpdateFname(membre, Fname);
					}
					//Verification MDP
					if ((!pass.equals(pass2)) && ((!pass.equals(null) && !pass.equals("")) || (!pass2.equals(null) && !pass2.equals("")))) {
						BorderError(PF_pass, icon_4);
						BorderError(PF_passConf, icon_5);
						error_msg.add("- Les mots de passes ne sont pas identiques.");
					} else if (pass.length() < 8 && !pass.equals("") && !pass.equals(null)) {
						error_msg.add("- Le mot de passe doit contenir au moins 8 caractères!");
					} else if (pass.equals(pass2) && !pass.equals(null) && !pass.equals("")) {
						membreDAO.getInstance().UpdatePass(membre, pass);
						
						error_msg.add("- Votre mot de passe a été modifier.");
					}

					for (String msg : error_msg) {
						error_msg_final = "<html>" + error_msg_final + "<br>" + msg;
					}

					lbl_error_msg.setText(error_msg_final);
				} else {
					lbl_error_msg.setText("Mot de passe actuel incorrect.");
				}
			}
		});
		btn_submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_submit.setBackground(new Color(252, 129, 74));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_submit.setBackground(new Color(153, 78, 44));
			}
		});
		btn_submit.setFocusable(false);
		btn_submit.setBorderPainted(false);
		btn_submit.setForeground(Color.WHITE);
		btn_submit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_submit.setBackground(new Color(153, 78, 44));
		btn_submit.setBounds(140, 311, 300, 40);
		add(btn_submit);

	}

	public void BorderSelected(JTextField txtField, JLabel icon) {
		Color color_TF_IN = new Color(252, 129, 74);
		CompoundBorder border_IN = new CompoundBorder(new LineBorder(color_TF_IN, 2),
				BorderFactory.createEmptyBorder(0, 5, 0, 0));
		LineBorder Icon_IN = new LineBorder(color_TF_IN, 2);

		txtField.setBorder(border_IN);
		icon.setBorder(Icon_IN);
	}

	public void BorderNormal(JTextField txtField, JLabel icon) {
		Color color_TF_OUT = new Color(192, 192, 192);
		CompoundBorder border_OUT = new CompoundBorder(new LineBorder(color_TF_OUT, 2),
				BorderFactory.createEmptyBorder(0, 5, 0, 0));
		LineBorder Icon_OUT = new LineBorder(color_TF_OUT, 2);

		txtField.setBorder(border_OUT);
		icon.setBorder(Icon_OUT);
	}

	public void BorderError(JTextField txtField, JLabel icon) {
		Color color_Error = new Color(189, 30, 30);
		CompoundBorder Border_Error = new CompoundBorder(new LineBorder(color_Error, 2),
				BorderFactory.createEmptyBorder(0, 5, 0, 0));
		LineBorder Error_Icon = new LineBorder(color_Error, 2);

		txtField.setBorder(Border_Error);
		icon.setBorder(Error_Icon);
	}

	public String PasswordDialog() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Entrez votre mot de passe :");
		JPasswordField pass = new JPasswordField(25);
		panel.add(label);
		panel.add(pass);

		String[] options = new String[] { "Valider", "Annuler" };
		int option = JOptionPane.showOptionDialog(Main.getMainFrame(), panel, "Confirmation", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 0) {
			return pass.getText();
		} else {
			return null;
		}
	}
}
