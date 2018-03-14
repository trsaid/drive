package drive.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import drive.dao.loginDAO;
import drive.pojo.Membre;

public class home {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					home window = new home();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 300);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCompte = new JLabel("Compte");
		lblCompte.setBounds(88, 53, 155, 14);
		frame.getContentPane().add(lblCompte);
		
		lblCompte.setText("Compte : ");
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
