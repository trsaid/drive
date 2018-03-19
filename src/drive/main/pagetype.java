package drive.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class pagetype extends JFrame {

	private JPanel contentPane;
	private int xMouse, yMouse;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public pagetype() {
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

	}

}
