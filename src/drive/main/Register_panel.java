package drive.main;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.SystemColor;

public class Register_panel extends JPanel {
	private JTextField TF_Nom;
	private JTextField TF_Prenom;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public Register_panel() {
		setLayout(null);
		setBounds(10, 150, 580, 500);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 33, 35));
		panel.setBounds(0, 0, 580, 500);
		add(panel);
		panel.setLayout(null);
		
		/**
		 * Zone nom de compte.
		 */
		
		JLabel username_label = new JLabel("Nom de compte");
		username_label.setBounds(140, 11, 100, 14);
		panel.add(username_label);
		username_label.setForeground(UIManager.getColor("Button.light"));
		
		JTextField username = new JTextField();
		username.setBounds(140, 26, 300, 40);
		panel.add(username);
		username.setCaretColor(new Color(255, 255, 255));
		username.setForeground(new Color(255, 255, 255));
		username.setBorder(null);
		username.setBackground(new Color(32, 33, 35));
		username.setColumns(10);
		
		/**
		 * Zone MDP.
		 */
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(140, 69, 300, 1);
		panel.add(separator);
		
		/**
		 * Message erreur.
		 */
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(SystemColor.controlHighlight);
		lblNom.setBounds(140, 81, 100, 14);
		panel.add(lblNom);
		
		TF_Nom = new JTextField();
		TF_Nom.setForeground(Color.WHITE);
		TF_Nom.setColumns(10);
		TF_Nom.setCaretColor(Color.WHITE);
		TF_Nom.setBorder(null);
		TF_Nom.setBackground(new Color(32, 33, 35));
		TF_Nom.setBounds(140, 96, 300, 40);
		panel.add(TF_Nom);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(140, 139, 300, 1);
		panel.add(separator_2);
		
		JLabel lbl_Prenom = new JLabel("Pr\u00E9nom");
		lbl_Prenom.setForeground(SystemColor.controlHighlight);
		lbl_Prenom.setBounds(140, 151, 100, 14);
		panel.add(lbl_Prenom);
		
		TF_Prenom = new JTextField();
		TF_Prenom.setForeground(Color.WHITE);
		TF_Prenom.setColumns(10);
		TF_Prenom.setCaretColor(Color.WHITE);
		TF_Prenom.setBorder(null);
		TF_Prenom.setBackground(new Color(32, 33, 35));
		TF_Prenom.setBounds(140, 166, 300, 40);
		panel.add(TF_Prenom);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(Color.WHITE);
		separator_3.setBounds(140, 209, 300, 1);
		panel.add(separator_3);
		
		JLabel lbl_email = new JLabel("Adresse E-Mail");
		lbl_email.setForeground(SystemColor.controlHighlight);
		lbl_email.setBounds(140, 221, 100, 14);
		panel.add(lbl_email);
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setColumns(10);
		textField.setCaretColor(Color.WHITE);
		textField.setBorder(null);
		textField.setBackground(new Color(32, 33, 35));
		textField.setBounds(140, 236, 300, 40);
		panel.add(textField);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBackground(Color.WHITE);
		separator_4.setBounds(140, 279, 300, 1);
		panel.add(separator_4);
		
		JLabel password_label = new JLabel("Mot de passe");
		password_label.setBounds(140, 291, 84, 14);
		panel.add(password_label);
		password_label.setForeground(UIManager.getColor("Button.light"));
		
		JPasswordField password = new JPasswordField();
		password.setBounds(140, 306, 300, 40);
		panel.add(password);
		password.setCaretColor(new Color(255, 255, 255));
		password.setForeground(new Color(255, 255, 255));
		password.setBorder(null);
		password.setBackground(new Color(32, 33, 35));
		password.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(140, 350, 300, 1);
		panel.add(separator_1);
		
		JButton btnRegister = new JButton("Inscription");
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(new Color(0, 128, 128));
		btnRegister.setBounds(140, 428, 300, 40);
		panel.add(btnRegister);
		
		JLabel lbl_RegisterError = new JLabel("");
		lbl_RegisterError.setBounds(84, 470, 414, 30);
		panel.add(lbl_RegisterError);
		lbl_RegisterError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_RegisterError.setForeground(Color.RED);
		lbl_RegisterError.setFont(new Font("Courier New", Font.PLAIN, 17));
		
		JLabel lbl_PW_Conf = new JLabel("Confirmation");
		lbl_PW_Conf.setForeground(SystemColor.controlHighlight);
		lbl_PW_Conf.setBounds(140, 362, 84, 14);
		panel.add(lbl_PW_Conf);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.WHITE);
		passwordField.setColumns(10);
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBorder(null);
		passwordField.setBackground(new Color(32, 33, 35));
		passwordField.setBounds(140, 377, 300, 40);
		panel.add(passwordField);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(140, 421, 300, 1);
		panel.add(separator_5);

	}
}
