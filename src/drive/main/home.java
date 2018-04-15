package drive.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;
import drive.pojo.FTPUpload;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class home extends JFrame {

	private JPanel contentPane;
	
	private Upload_panel panel_upload;
	private Files_panel files_panel;
	
	private int xMouse, yMouse;
	public static JLabel lbl_progres_fichier_p;

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
		btnX.setBounds(771, 4, 25, 25);
		panel.add(btnX);

		/**
		 * Déplacement fenetre
		 */

		JPanel MotionPanel = new JPanel();
		MotionPanel.setOpaque(false);
		MotionPanel.setBackground(new Color(240, 248, 255));
		MotionPanel.setFocusable(false);
		MotionPanel.setBounds(0, 0, 800, 34);
		panel.add(MotionPanel);

		JLabel lblCompte = new JLabel("Compte : ");
		lblCompte.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblCompte.setForeground(Color.WHITE);
		lblCompte.setBounds(399, 116, 332, 52);
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
		
		
//		Intégration des panel
		
		files_panel = new Files_panel();
		files_panel.setLocation(0, 0);
		
		panel_upload = new Upload_panel();
		panel_upload.setLocation(0, 0);
		
//		Panel dynamique qui permet l'affichage des autres pannels
		JPanel panel_dyna = new JPanel();
		panel_dyna.setBounds(210, 213, 580, 296);
		panel.add(panel_dyna);
		panel_dyna.setBackground(new Color(32, 33, 35));
		panel_dyna.setLayout(null);
		
		panel_dyna.add(panel_upload);
		panel_dyna.add(files_panel);
		
//		Panel Menu
		JPanel menu_panel = new JPanel();
		menu_panel.setBounds(0, 0, 205, 520);
		menu_panel.setBackground(new Color(45, 45, 45));
		panel.add(menu_panel);
		menu_panel.setLayout(null);
		
		JPanel home_menu = new JPanel();
		home_menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		home_menu.setBounds(0, 150, 205, 45);
		home_menu.setBackground(new Color(66, 66, 66));
		menu_panel.add(home_menu);
		home_menu.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(home.class.getResource("/images/home.png")));
		label.setBounds(10, 0, 35, 45);
		home_menu.add(label);
		
		JLabel lblHome = new JLabel("Accueil");
		lblHome.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblHome.setForeground(new Color(255, 255, 255));
		lblHome.setBounds(66, 0, 139, 45);
		home_menu.add(lblHome);
		
		JPanel upload_menu = new JPanel();
		upload_menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		Action après click
		upload_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_upload.setVisible(true);
				files_panel.setVisible(false);
			}
		});
		upload_menu.setLayout(null);
		upload_menu.setBackground(new Color(50, 50, 50));
		upload_menu.setBounds(0, 195, 205, 45);
		menu_panel.add(upload_menu);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(home.class.getResource("/images/upload2.png")));
		label_1.setBounds(10, 0, 35, 45);
		upload_menu.add(label_1);
		
		JLabel lblEnvoyerUnFichier = new JLabel("Envoyer un fichier");
		lblEnvoyerUnFichier.setForeground(Color.WHITE);
		lblEnvoyerUnFichier.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblEnvoyerUnFichier.setBounds(66, 0, 139, 45);
		upload_menu.add(lblEnvoyerUnFichier);
		
		JPanel files_menu = new JPanel();
		files_menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		Action après click
		files_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				files_panel.setVisible(true);
				panel_upload.setVisible(false);
			}
		});
		files_menu.setLayout(null);
		files_menu.setBackground(new Color(50, 50, 50));
		files_menu.setBounds(0, 240, 205, 45);
		menu_panel.add(files_menu);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(home.class.getResource("/images/files.png")));
		label_2.setBounds(10, 0, 35, 45);
		files_menu.add(label_2);
		
		JLabel lblMesFichiers = new JLabel("Mes fichiers");
		lblMesFichiers.setForeground(Color.WHITE);
		lblMesFichiers.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMesFichiers.setBounds(66, 0, 139, 45);
		files_menu.add(lblMesFichiers);
		
		JPanel share_menu = new JPanel();
		share_menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		share_menu.setLayout(null);
		share_menu.setBackground(new Color(50, 50, 50));
		share_menu.setBounds(0, 285, 205, 45);
		menu_panel.add(share_menu);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(home.class.getResource("/images/share.png")));
		label_4.setBounds(10, 0, 35, 45);
		share_menu.add(label_4);
		
		JLabel lblPartagAvecMoi = new JLabel("Partag\u00E9 avec moi");
		lblPartagAvecMoi.setForeground(Color.WHITE);
		lblPartagAvecMoi.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblPartagAvecMoi.setBounds(66, 0, 139, 45);
		share_menu.add(lblPartagAvecMoi);
		
		JPanel account_menu = new JPanel();
		account_menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		account_menu.setLayout(null);
		account_menu.setBackground(new Color(50, 50, 50));
		account_menu.setBounds(0, 330, 205, 45);
		menu_panel.add(account_menu);
		
		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon(home.class.getResource("/images/account.png")));
		label_6.setBounds(10, 0, 35, 45);
		account_menu.add(label_6);
		
		JLabel lblMonCompte = new JLabel("Mon compte");
		lblMonCompte.setForeground(Color.WHITE);
		lblMonCompte.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMonCompte.setBounds(66, 0, 139, 45);
		account_menu.add(lblMonCompte);
		
		JLabel label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setIcon(new ImageIcon(home.class.getResource("/images/cloud.png")));
		label_3.setBounds(0, 0, 205, 146);
		menu_panel.add(label_3);
		
		panel_upload.setVisible(true);
		files_panel.setVisible(false);

//		modifyLabel();

	}
}
