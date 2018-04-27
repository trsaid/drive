package drive.main;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import drive.dao.loginDAO;
import drive.dao.membreDAO;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register_panel extends JPanel {
	private JTextField TF_Nom;
	private JTextField TF_Prenom;
	private JTextField TF_email;
	private JPasswordField PF_password_conf;
	
	private membreDAO membredao;

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
		 * Zone nom.
		 */

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(140, 69, 300, 1);
		panel.add(separator);

		JLabel lbl_Nom = new JLabel("Nom");
		lbl_Nom.setForeground(SystemColor.controlHighlight);
		lbl_Nom.setBounds(140, 81, 100, 14);
		panel.add(lbl_Nom);

		TF_Nom = new JTextField();
		TF_Nom.setForeground(Color.WHITE);
		TF_Nom.setColumns(10);
		TF_Nom.setCaretColor(Color.WHITE);
		TF_Nom.setBorder(null);
		TF_Nom.setBackground(new Color(32, 33, 35));
		TF_Nom.setBounds(140, 96, 300, 40);
		panel.add(TF_Nom);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(140, 139, 300, 1);
		panel.add(separator_1);

		/**
		 * Zone prénom.
		 */
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

		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(140, 209, 300, 1);
		panel.add(separator_2);

		/**
		 * Zone email.
		 */
		JLabel lbl_email = new JLabel("Adresse E-Mail");
		lbl_email.setForeground(SystemColor.controlHighlight);
		lbl_email.setBounds(140, 221, 100, 14);
		panel.add(lbl_email);

		TF_email = new JTextField();
		TF_email.setForeground(Color.WHITE);
		TF_email.setColumns(10);
		TF_email.setCaretColor(Color.WHITE);
		TF_email.setBorder(null);
		TF_email.setBackground(new Color(32, 33, 35));
		TF_email.setBounds(140, 236, 300, 40);
		panel.add(TF_email);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(Color.WHITE);
		separator_3.setBounds(140, 279, 300, 1);
		panel.add(separator_3);

		JLabel lbl_password = new JLabel("Mot de passe");
		lbl_password.setBounds(140, 291, 84, 14);
		panel.add(lbl_password);
		lbl_password.setForeground(UIManager.getColor("Button.light"));

		/**
		 * Zone MDP.
		 */

		JPasswordField PF_password = new JPasswordField();
		PF_password.setBounds(140, 306, 300, 40);
		panel.add(PF_password);
		PF_password.setCaretColor(new Color(255, 255, 255));
		PF_password.setForeground(new Color(255, 255, 255));
		PF_password.setBorder(null);
		PF_password.setBackground(new Color(32, 33, 35));
		PF_password.setColumns(10);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(140, 350, 300, 1);
		panel.add(separator_4);

		/**
		 * Zone confirmation MDP.
		 */
		JLabel lbl_PW_Conf = new JLabel("Confirmation");
		lbl_PW_Conf.setForeground(SystemColor.controlHighlight);
		lbl_PW_Conf.setBounds(140, 362, 84, 14);
		panel.add(lbl_PW_Conf);

		PF_password_conf = new JPasswordField();
		PF_password_conf.setForeground(Color.WHITE);
		PF_password_conf.setColumns(10);
		PF_password_conf.setCaretColor(Color.WHITE);
		PF_password_conf.setBorder(null);
		PF_password_conf.setBackground(new Color(32, 33, 35));
		PF_password_conf.setBounds(140, 377, 300, 40);
		panel.add(PF_password_conf);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(140, 421, 300, 1);
		panel.add(separator_5);

		/**
		 * Bouton d'inscription
		 */
		JButton btn_Register = new JButton("Inscription");
		btn_Register.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_Register.setForeground(Color.WHITE);
		btn_Register.setBackground(new Color(0, 128, 128));
		btn_Register.setBounds(140, 428, 300, 40);
		panel.add(btn_Register);

		/**
		 * Message erreur.
		 */

		JLabel lbl_RegisterError = new JLabel("");
		lbl_RegisterError.setBounds(84, 470, 414, 30);
		panel.add(lbl_RegisterError);
		lbl_RegisterError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_RegisterError.setForeground(Color.RED);
		lbl_RegisterError.setFont(new Font("Courier New", Font.PLAIN, 17));

		/**
		 * Retour à la page de connexion.
		 */
		JLabel back_login = new JLabel("");
		back_login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back_login.setBounds(30, 139, 80, 120);
		panel.add(back_login);
		back_login.setHorizontalAlignment(SwingConstants.CENTER);
		back_login.setIcon(new ImageIcon(Register_panel.class.getResource("/images/left_arrow.png")));

		back_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				main.RegisterPanel.setVisible(false);
				main.login_panel.setVisible(true);
			}
		});

		/**
		 * Zone inscription.
		 */
		btn_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = username.getText();
				String lName = TF_Nom.getText();
				String fName = TF_Prenom.getText();
				String email = TF_email.getText();
				String pass = PF_password.getText();
				String Cpass = PF_password_conf.getText();
				
				boolean userExist = false;

				if (uname.isEmpty()) {
					lbl_RegisterError.setText("Veuillez entrer un nom de compte.");
				} else if (lName.isEmpty()) {
					lbl_RegisterError.setText("Veuillez entrer votre nom.");
				} else if (fName.isEmpty()) {
					lbl_RegisterError.setText("Veuillez entrer votre prénom.");
				} else if (email.isEmpty()) {
					lbl_RegisterError.setText("Veuillez entrer une adresse email.");
				} else if (pass.isEmpty()) {
					lbl_RegisterError.setText("Veuillez entrer un mot de passe.");
				} else if (Cpass.isEmpty()) {
					lbl_RegisterError.setText("Veuillez confirmer mot de passe.");
				} else {
					try {
						userExist = membredao.getInstance().userExist(uname);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (userExist) {
						lbl_RegisterError.setText("Ce nom d'utilisateur éxiste déjà!");
					} else {
						try {
							membredao.getInstance().register(uname, lName, fName, email, pass);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		});

	}

}
