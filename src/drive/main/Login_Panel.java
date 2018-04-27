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
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;
import javax.swing.border.BevelBorder;

public class Login_Panel extends JPanel {

	private loginDAO logindao;
	/**
	 * Create the panel.
	 */
	public Login_Panel() {
		setLayout(null);
		setBounds(10, 150, 580, 450);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(140, 186, 300, 1);
		add(separator);
		
		/**
		 * Titre
		 */
		
		JLabel lbl_login_title = new JLabel("Page de connexion");
		lbl_login_title.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 200, 0), new Color(255, 200, 0), new Color(255, 175, 175), Color.PINK));
		lbl_login_title.setFont(new Font("Raleway ExtraBold", Font.BOLD, 18));
		lbl_login_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_login_title.setForeground(Color.WHITE);
		lbl_login_title.setBounds(140, 50, 300, 42);
		add(lbl_login_title);
		
		/**
		 * Zone nom de compte.
		 */
		
		JLabel username_label = new JLabel("Nom de compte");
		username_label.setBounds(140, 111, 100, 14);
		add(username_label);
		username_label.setForeground(UIManager.getColor("Button.light"));
		
		JTextField username = new JTextField();
		username.setBounds(140, 126, 300, 40);
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
		password_label.setBounds(140, 191, 84, 14);
		add(password_label);
		password_label.setForeground(UIManager.getColor("Button.light"));
		
		JPasswordField password = new JPasswordField();
		password.setBounds(140, 206, 300, 40);
		add(password);
		password.setCaretColor(new Color(255, 255, 255));
		password.setForeground(new Color(255, 255, 255));
		password.setBorder(null);
		password.setBackground(new Color(32, 33, 35));
		password.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(140, 266, 300, 1);
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
		chckbx_Save.setBounds(140, 277, 300, 23);
		add(chckbx_Save);
		JButton loginButton = new JButton("Se connecter");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(0, 128, 128));
		loginButton.setBounds(140, 311, 300, 40);
		add(loginButton);
		
		
		/**
		 * Bouton inscription
		 */
		
		JLabel lblRegister = new JLabel("Toujours pas de compte?");
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				main.login_panel.setVisible(false);
				main.RegisterPanel.setVisible(true);
			}
		});
		lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setForeground(Color.GREEN);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRegister.setBounds(140, 362, 300, 20);
		add(lblRegister);
		
		/**
		 * Message erreur.
		 */
		
		JLabel lbl_LoginError = new JLabel("");
		lbl_LoginError.setBounds(94, 386, 414, 30);
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
					lbl_LoginError.setText("Veuillez entré un nom de compte.");
				} else if (password.getText().isEmpty()) {
					lbl_LoginError.setText("Veuillez entré un mot de passe.");
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
