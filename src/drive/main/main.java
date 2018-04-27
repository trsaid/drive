package drive.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class main extends JFrame {

	private JPanel contentPane;
	
	private int xMouse, yMouse;
	
	public static JPanel RegisterPanel = new Register_panel();
	public static JPanel login_panel = new Login_Panel();

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
		setBounds(100, 100, 600, 657);
		setLocationRelativeTo(null); // Permet de centrer le programme
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		getContentPane().add(login_panel);
		login_panel.setVisible(true);
		getContentPane().add(RegisterPanel);
		RegisterPanel.setVisible(false);
		
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
		 * Boutton quitt�
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
		 * D�placement fenetre
		 */
		
		
	}
}
