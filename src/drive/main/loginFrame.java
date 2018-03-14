package drive.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;

public class loginFrame extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private loginDAO logindao;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public loginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel username_label = new JLabel("Nom de compte");
		username_label.setBounds(31, 81, 84, 14);
		contentPane.add(username_label);
		
		JLabel password_label = new JLabel("Mot de passe");
		password_label.setBounds(31, 135, 84, 14);
		contentPane.add(password_label);
		
		username = new JTextField();
		username.setBounds(172, 78, 150, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(172, 132, 150, 20);
		contentPane.add(password);
		
		JButton loginButton = new JButton("Connexion");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = username.getText();
				String pass = password.getText();
				int logged = 0;

				try {
					logged = logindao.getInstance().login(uname, pass);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (logged == 0) {
					JOptionPane.showMessageDialog(frame, "Nom de compte invalide");
				} else {
//					login.dispose();
					List<Membre> user = logindao.getInstance().userInfo(logged);
					home h = new home();
					h.getFrame().setVisible(true);
				}
			}
		});
		loginButton.setBounds(172, 227, 150, 23);
		contentPane.add(loginButton);
	}
}
