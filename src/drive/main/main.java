package drive.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class main extends JFrame {

	private JPanel contentPane;
	
	private int xMouse, yMouse;

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
		setTitle("Cloud - Connexion");
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 668);
		setLocationRelativeTo(null); // Permet de centrer le programme
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnX = new JButton("\u2715");
		btnX.setBounds(560, 0, 40, 34);
		contentPane.add(btnX);
		btnX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnX.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnX.setOpaque(false);
			}
		});
		btnX.setOpaque(false);
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnX.setBorder(null);
		btnX.setFocusable(false);
		btnX.setForeground(Color.WHITE);
		btnX.setBackground(Color.RED);
		
		JPanel MotionPanel = new JPanel();
		MotionPanel.setBounds(0, 0, 600, 34);
		contentPane.add(MotionPanel);
		MotionPanel.setOpaque(false);
		MotionPanel.setFocusable(false);
		
		
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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 657);
		panel.setBackground(new Color(32, 33, 35));
		contentPane.add(panel);
		panel.setLayout(null);
		
		/**
		 * Boutton quitté
		 */
		
		/**
		 * Logo
		 */
		
		JLabel label_img = new JLabel("");
		label_img.setFocusable(false);
		label_img.setIcon(new ImageIcon(main.class.getResource("/images/cloud.png")));
		label_img.setBounds(214, 18, 172, 111);
		panel.add(label_img);
		
		/**
		 * Déplacement fenetre
		 */
		
		/**
		 * Bouton inscription
		 */
		
		JLabel lblRegister = new JLabel("Toujours pas de compte?");
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPanel RegisterPanel = new Register_panel();
				panel.add(RegisterPanel);
				RegisterPanel.setVisible(true);
			}
		});
		lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setForeground(Color.GREEN);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRegister.setBounds(150, 396, 300, 20);
		panel.add(lblRegister);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 131, 600, 526);
		panel_1.setBackground(new Color(32,33,35));
		panel.add(panel_1);
		
		
	}
}
