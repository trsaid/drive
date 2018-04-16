package drive.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.SystemColor;

public class home extends JFrame {

	private JPanel contentPane;

	private JPanel home_panel;
	private JPanel panel_upload;
	private JPanel files_panel;
	private JPanel share_panel;
	private JPanel archives_panel;
	private JPanel account_panel;

	private int xMouse, yMouse;
	public static JLabel lbl_progres_fichier_p;
	
	//Menu panel
	
	private JPanel[] Menu_Panel = new JPanel[6];
	private boolean[] Menu = new boolean[6];
	private JPanel Menu_active;
	


	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public home(int user_id) {
		List<Membre> user = loginDAO.getInstance().userInfo(user_id);
		String user_name = user.get(0).getUsername();

		setTitle("Cloud - " + user_name);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 520);
		setLocationRelativeTo(null); // Permet de centrer le programme

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 33, 35));
		panel.setBounds(0, 0, 800, 520);
		contentPane.add(panel);
		panel.setLayout(null);

		/**
		 * Boutton quitté
		 */

		JButton btnX = new JButton("\u2715");
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
		btnX.setBounds(760, 0, 40, 34);
		panel.add(btnX);

		/**
		 * Déplacement fenetre
		 */
		
		JButton btnMinimise = new JButton("__");
		btnMinimise.setFocusable(false);
		btnMinimise.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMinimise.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMinimise.setOpaque(false);
			}
		});
		btnMinimise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnMinimise.setOpaque(false);
		btnMinimise.setBackground(SystemColor.activeCaption);
		btnMinimise.setBorder(null);
		btnMinimise.setForeground(Color.WHITE);
		btnMinimise.setBounds(720, 0, 40, 34);
		panel.add(btnMinimise);

		JPanel MotionPanel = new JPanel();
		MotionPanel.setOpaque(false);
		MotionPanel.setBackground(new Color(240, 248, 255));
		MotionPanel.setFocusable(false);
		MotionPanel.setBounds(0, 0, 800, 34);
		panel.add(MotionPanel);

		JLabel lblCompte = new JLabel("Compte : ");
		lblCompte.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblCompte.setForeground(Color.WHITE);
		lblCompte.setBounds(358, 45, 332, 52);
		panel.add(lblCompte);
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

		lblCompte.setText("Compte : " + user_name);

		/**
		 *  Intégration des panel
		 */


		
		home_panel = new Home_panel();
		home_panel.setLocation(0, 0);
		
		panel_upload = new Upload_panel();
		panel_upload.setLocation(0, 0);
		
		files_panel = new Files_panel();
		files_panel.setLocation(0, 0);
		
		share_panel = new Share_panel();
		share_panel.setLocation(0, 0);
		
		archives_panel = new Archives_panel();
		archives_panel.setLocation(0, 0);
		
		account_panel = new Account_panel();
		account_panel.setLocation(0, 0);


		/**
		 * Panel dynamique qui permet l'affichage des autres pannels
		 */
		JPanel panel_dyna = new JPanel();
		panel_dyna.setBounds(210, 213, 580, 296);
		panel.add(panel_dyna);
		panel_dyna.setBackground(new Color(32, 33, 35));
		panel_dyna.setLayout(null);

		panel_dyna.add(home_panel);
		panel_dyna.add(panel_upload);
		panel_dyna.add(files_panel);
		panel_dyna.add(share_panel);
		panel_dyna.add(archives_panel);
		panel_dyna.add(account_panel);

		/**
		 *  Panel Menu
		 */
		JPanel menu_panel = new JPanel();
		menu_panel.setBounds(0, 0, 205, 520);
		menu_panel.setBackground(new Color(45, 45, 45));
		panel.add(menu_panel);
		menu_panel.setLayout(null);

		JLabel label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setIcon(new ImageIcon(home.class.getResource("/images/cloud.png")));
		label_3.setBounds(0, 0, 205, 146);
		menu_panel.add(label_3);
		
		/**
		 * Initialisation
		 */
		Menu[0] = true;
		Menu_active = home_panel;
		home_panel.setVisible(true);
		

		/**
		 * Bouton Accueil
		 */
		Menu_Panel[0] = new JPanel();
		Menu_Panel[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 0);
				
				Menu_active = home_panel;
				home_panel.setVisible(true);

				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				parent.setBackground(new Color(66, 66, 66));
				parent.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				if(!Menu[0]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Menu_Panel[0].setBounds(0, 150, 205, 45);
		Menu_Panel[0].setBackground(new Color(66, 66, 66));
		menu_panel.add(Menu_Panel[0]);
		Menu_Panel[0].setLayout(null);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(home.class.getResource("/images/home.png")));
		label.setBounds(0, 0, 56, 45);
		Menu_Panel[0].add(label);

		JLabel lblHome = new JLabel("Accueil");
		lblHome.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblHome.setForeground(new Color(255, 255, 255));
		lblHome.setBounds(66, 0, 139, 45);
		Menu_Panel[0].add(lblHome);
		
		/**
		 * Bouton Envoyer un fichier
		 */

		Menu_Panel[1] = new JPanel();
		Menu_Panel[1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Menu_Panel[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 1);
				
				Menu_active = panel_upload;
				panel_upload.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				parent.setBackground(new Color(66, 66, 66));
				parent.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				if(!Menu[1]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[1].setLayout(null);
		Menu_Panel[1].setBackground(new Color(50, 50, 50));
		Menu_Panel[1].setBounds(0, 195, 205, 45);
		menu_panel.add(Menu_Panel[1]);

		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setIcon(new ImageIcon(home.class.getResource("/images/upload2.png")));
		label_1.setBounds(0, 0, 56, 45);
		Menu_Panel[1].add(label_1);

		JLabel lblEnvoyerUnFichier = new JLabel("Envoyer un fichier");
		lblEnvoyerUnFichier.setForeground(Color.WHITE);
		lblEnvoyerUnFichier.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblEnvoyerUnFichier.setBounds(66, 0, 139, 45);
		Menu_Panel[1].add(lblEnvoyerUnFichier);
		
		/**
		 * Bouton Mes fichiers
		 */

		Menu_Panel[2] = new JPanel();
		Menu_Panel[2].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Action après click
		Menu_Panel[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 2);
				
				Menu_active = files_panel;
				files_panel.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				parent.setBackground(new Color(66, 66, 66));
				parent.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel) e.getSource();
				if(!Menu[2]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[2].setLayout(null);
		Menu_Panel[2].setBackground(new Color(50, 50, 50));
		Menu_Panel[2].setBounds(0, 240, 205, 45);
		menu_panel.add(Menu_Panel[2]);

		JLabel label_2 = new JLabel("");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setIcon(new ImageIcon(home.class.getResource("/images/files.png")));
		label_2.setBounds(0, 0, 56, 45);
		Menu_Panel[2].add(label_2);

		JLabel lblMesFichiers = new JLabel("Mes fichiers");
		lblMesFichiers.setForeground(Color.WHITE);
		lblMesFichiers.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMesFichiers.setBounds(66, 0, 139, 45);
		Menu_Panel[2].add(lblMesFichiers);
		
		/**
		 * Bouton partagé avec moi
		 */

		Menu_Panel[3] = new JPanel();
		Menu_Panel[3].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Menu_Panel[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 3);
				
				Menu_active = share_panel;
				share_panel.setVisible(true);

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
			    parent.setBackground(new Color(66, 66, 66));
			    parent.revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
				if(!Menu[3]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[3].setLayout(null);
		Menu_Panel[3].setBackground(new Color(50, 50, 50));
		Menu_Panel[3].setBounds(0, 285, 205, 45);
		menu_panel.add(Menu_Panel[3]);

		JLabel label_4 = new JLabel("");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setIcon(new ImageIcon(home.class.getResource("/images/share.png")));
		label_4.setBounds(0, 0, 56, 45);
		Menu_Panel[3].add(label_4);

		JLabel lblPartagAvecMoi = new JLabel("Partag\u00E9 avec moi");
		lblPartagAvecMoi.setForeground(Color.WHITE);
		lblPartagAvecMoi.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblPartagAvecMoi.setBounds(66, 0, 139, 45);
		Menu_Panel[3].add(lblPartagAvecMoi);
		
		/**
		 * Bouton archives
		 */
		Menu_Panel[4] = new JPanel();
		Menu_Panel[4].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Menu_Panel[4].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 4);
				
				Menu_active = archives_panel;
				archives_panel.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
			    parent.setBackground(new Color(66, 66, 66));
			    parent.revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
				if(!Menu[4]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[4].setLayout(null);
		Menu_Panel[4].setBackground(new Color(50, 50, 50));
		Menu_Panel[4].setBounds(0, 330, 205, 45);
		menu_panel.add(Menu_Panel[4]);

		JLabel label_5 = new JLabel("");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setIcon(new ImageIcon(home.class.getResource("/images/archive.png")));
		label_5.setBounds(0, 0, 56, 45);
		Menu_Panel[4].add(label_5);

		JLabel lblArchives = new JLabel("Archives");
		lblArchives.setForeground(Color.WHITE);
		lblArchives.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblArchives.setBounds(66, 0, 139, 45);
		Menu_Panel[4].add(lblArchives);

		/**
		 * Bouton Mon compte
		 */
		Menu_Panel[5] = new JPanel();
		Menu_Panel[5].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Menu_Panel[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Reset
				MenuReset();
				
				MenuClick(e, 5);
				
				Menu_active = account_panel;
				account_panel.setVisible(true);

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
			    parent.setBackground(new Color(66, 66, 66));
			    parent.revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JPanel parent = (JPanel)e.getSource();
				if(!Menu[5]) {
					parent.setBackground(new Color(50, 50, 50));
					parent.revalidate();
				}
			}
		});
		Menu_Panel[5].setLayout(null);
		Menu_Panel[5].setBackground(new Color(50, 50, 50));
		Menu_Panel[5].setBounds(0, 375, 205, 45);
		menu_panel.add(Menu_Panel[5]);

		JLabel label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setIcon(new ImageIcon(home.class.getResource("/images/account.png")));
		label_6.setBounds(0, 0, 56, 45);
		Menu_Panel[5].add(label_6);

		JLabel lblMonCompte = new JLabel("Mon compte");
		lblMonCompte.setForeground(Color.WHITE);
		lblMonCompte.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMonCompte.setBounds(66, 0, 139, 45);
		Menu_Panel[5].add(lblMonCompte);


	}
	
	public void MenuClick(MouseEvent e, int nb) {
		JPanel parent = (JPanel) e.getSource();
		parent.setBackground(new Color(66, 66, 66));
		
		Menu_active.setVisible(false);
		
		Menu[nb] = true;
	}
	
	public void MenuReset() {
		Color color = new Color(50, 50, 50);
		
		for(int i = 0; i < Menu.length; i++) {
			Menu[i] = false;
			Menu_Panel[i].setBackground(color);
		}
	}
}
