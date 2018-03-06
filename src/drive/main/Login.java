package drive.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import drive.dao.loginDAO;
import drive.dao.membreDAO;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	public JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private membreDAO membre;
	private loginDAO login;
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNomDeCompte = new JLabel("Nom de compte");
		lblNomDeCompte.setBounds(72, 94, 89, 14);
		frame.getContentPane().add(lblNomDeCompte);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setBounds(72, 131, 89, 14);
		frame.getContentPane().add(lblMotDePasse);
		
		username = new JTextField();
		username.setBounds(171, 91, 137, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(171, 128, 137, 20);
		frame.getContentPane().add(password);
		
		JButton btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = username.getText();
				String pass = password.getText();
//				boolean logged = false;
//				try {
					boolean logged = login.login(uname, pass);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				JOptionPane.showMessageDialog(frame, logged);
				if(Boolean.TRUE.equals(logged)) {
					JOptionPane.showMessageDialog(frame, "Nom de compte valide");
				}else {
					JOptionPane.showMessageDialog(frame, "Nom de compte invalide");
				}
			}
		});
		btnSeConnecter.setBounds(193, 173, 95, 23);
		frame.getContentPane().add(btnSeConnecter);
	}
}
