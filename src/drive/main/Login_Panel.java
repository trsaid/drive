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

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class Login_Panel extends JPanel {

	private loginDAO logindao;
	/**
	 * Create the panel.
	 */
	public Login_Panel() {
		setLayout(null);
		setBounds(10, 150, 580, 325);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(150, 86, 300, 1);
		add(separator);
		
		/**
		 * Zone nom de compte.
		 */
		
		JLabel username_label = new JLabel("Nom de compte");
		username_label.setBounds(150, 11, 100, 14);
		add(username_label);
		username_label.setForeground(UIManager.getColor("Button.light"));
		
		JTextField username = new JTextField();
		username.setBounds(150, 26, 300, 40);
		add(username);
		username.setCaretColor(new Color(255, 255, 255));
		username.setForeground(new Color(255, 255, 255));
		username.setBorder(null);
		username.setBackground(new Color(32, 33, 35));
		username.setColumns(10);
		
		/**
		 * Zone MDP.
		 */
		
		JLabel password_label = new JLabel("Mot de passe");
		password_label.setBounds(150, 91, 84, 14);
		add(password_label);
		password_label.setForeground(UIManager.getColor("Button.light"));
		
		JPasswordField password = new JPasswordField();
		password.setBounds(150, 106, 300, 40);
		add(password);
		password.setCaretColor(new Color(255, 255, 255));
		password.setForeground(new Color(255, 255, 255));
		password.setBorder(null);
		password.setBackground(new Color(32, 33, 35));
		password.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(150, 166, 300, 1);
		add(separator_1);
		
		/**
		 * Zone Se souvenir de moi.
		 */
		
		JCheckBox chckbx_Save = new JCheckBox("Se souvenir de moi");
		
		if(pref.get("username", null) == null || pref.get("password", null) == null || pref.get("save", null) == null) {
			pref.put("save", "non");
			saveUser("", "");
		}
		
		if(loadCheck().equals("oui")) {
			chckbx_Save.setSelected(true);
			username.setText(loadUsername());
			password.setText(loadPassword());
		}
		
		
		chckbx_Save.setOpaque(false);
		chckbx_Save.setFocusable(false);
		chckbx_Save.setForeground(Color.WHITE);
		chckbx_Save.setContentAreaFilled(false);
		chckbx_Save.setBorder(null);
		chckbx_Save.setBounds(150, 181, 300, 23);
		add(chckbx_Save);
		JButton loginButton = new JButton("Connexion");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(0, 128, 128));
		loginButton.setBounds(150, 211, 300, 40);
		add(loginButton);
		
		/**
		 * Message erreur.
		 */
		
		JLabel lbl_LoginError = new JLabel("");
		lbl_LoginError.setBounds(94, 270, 414, 30);
		add(lbl_LoginError);
		lbl_LoginError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LoginError.setForeground(Color.RED);
		lbl_LoginError.setFont(new Font("Courier New", Font.PLAIN, 17));
		
		
		
		/**
		 * Zone login.
		 */
		
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = username.getText();
				String pass = password.getText();
				int logged = 0;

				
				if (username.getText().isEmpty()) {
					lbl_LoginError.setText("Veuillez entr� un nom de compte.");
				} else if (password.getText().isEmpty()) {
					lbl_LoginError.setText("Veuillez entr� un mot de passe.");
				} else {
					try {
						logged = logindao.getInstance().login(uname, pass);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (logged == 0) {
						lbl_LoginError.setText("Nom de compte ou mot de passe invalide");
					} else {
						if(chckbx_Save.isSelected()) {
							pref.put("save", "oui");
							saveUser(uname, pass);
						}
						home h = new home(logged);
						h.setVisible(true);
						setVisible(false);
						
					}
				}
			}
		});

	}
	Preferences pref = Preferences.userNodeForPackage(main.class);
	public void saveUser(String username, String password) {
		pref.put("username", username);
		pref.put("password", password);
	}
	
	public String loadUsername() {
		return pref.get("username", null);
	}

	public String loadPassword() {
		return pref.get("password", null);
	}
	public String loadCheck() {
		return pref.get("save", null);
	}
}
