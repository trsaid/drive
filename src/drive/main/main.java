package drive.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class main extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private loginDAO logindao;
	
	private int xMouse, yMouse;
	
	
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

	/**
	 * Launch the application.
	 */
		public static void main(String[] args) {
			

			main login = new main();
			login.setVisible(true);
		}

	/**
	 * Create the frame.
	 */
	public main() {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setLocationRelativeTo(null); // Permet de centrer le programme
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 33, 35));
		panel.setBounds(0, 0, 600, 450);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(150, 225, 300, 1);
		panel.add(separator);
		
		/**
		 * Zone nom de compte.
		 */
		
		JLabel username_label = new JLabel("Nom de compte");
		username_label.setBounds(150, 150, 100, 14);
		panel.add(username_label);
		username_label.setForeground(UIManager.getColor("Button.light"));
		
		username = new JTextField();
		username.setBounds(150, 165, 300, 40);
		panel.add(username);
		username.setCaretColor(new Color(255, 255, 255));
		username.setForeground(new Color(255, 255, 255));
		username.setBorder(null);
		username.setBackground(new Color(32, 33, 35));
		username.setColumns(10);
		
		/**
		 * Zone MDP.
		 */
		
		JLabel password_label = new JLabel("Mot de passe");
		password_label.setBounds(150, 230, 84, 14);
		panel.add(password_label);
		password_label.setForeground(UIManager.getColor("Button.light"));
		
		password = new JPasswordField();
		password.setBounds(150, 245, 300, 40);
		panel.add(password);
		password.setCaretColor(new Color(255, 255, 255));
		password.setForeground(new Color(255, 255, 255));
		password.setBorder(null);
		password.setBackground(new Color(32, 33, 35));
		password.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(150, 305, 300, 1);
		panel.add(separator_1);
		
		/**
		 * Zone Se souvenir de moi.
		 */
		
		JCheckBox chckbx_Save = new JCheckBox("Se souvenir de moi");
		
		
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
		chckbx_Save.setBounds(150, 320, 300, 23);
		panel.add(chckbx_Save);
		JButton loginButton = new JButton("Connexion");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(0, 128, 128));
		loginButton.setBounds(150, 350, 300, 40);
		panel.add(loginButton);
		
		/**
		 * Message erreur.
		 */
		
		JLabel lbl_LoginError = new JLabel("");
		lbl_LoginError.setBounds(94, 409, 414, 30);
		panel.add(lbl_LoginError);
		lbl_LoginError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LoginError.setForeground(Color.RED);
		lbl_LoginError.setFont(new Font("Courier New", Font.PLAIN, 17));
		
		/**
		 * Boutton quitté
		 */
		
		JButton btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnX.setBorder(null);
		btnX.setFocusable(false);
		btnX.setForeground(Color.WHITE);
		btnX.setBackground(Color.RED);
		btnX.setBounds(565, 11, 25, 23);
		panel.add(btnX);
		
		/**
		 * Logo
		 */
		
		JLabel label_img = new JLabel("");
		label_img.setFocusable(false);
		label_img.setIcon(new ImageIcon("C:\\Users\\trsai\\Desktop\\java\\cloud.png"));
		label_img.setBounds(214, 18, 172, 111);
		panel.add(label_img);
		
		/**
		 * Déplacement fenetre
		 */
		
		JPanel MotionPanel = new JPanel();
		MotionPanel.setOpaque(false);
		MotionPanel.setFocusable(false);
		MotionPanel.setBounds(0, 0, 600, 34);
		panel.add(MotionPanel);
		
		MotionPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				

				setLocation(x - xMouse, y - yMouse);
			}
		});
		
		MotionPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		
		
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
}
