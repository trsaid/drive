package drive.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import drive.dao.dossierDAO;
import drive.dao.loginDAO;
import drive.dao.membreDAO;
import drive.pojo.Dossier;
import drive.pojo.Membre;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.JProgressBar;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;

public class home extends JFrame {

	private JPanel contentPane;

	private int xMouse, yMouse;

	// Menu panel

	private JPanel[] Menu_Panel = new JPanel[5];
	private boolean[] Menu = new boolean[5];
	private static JPanel Menu_active;

	// progressbar
	public static JProgressBar progressBar_total;
	public static JLabel lbl_progres_total;
	public static JLabel lbl_progres_fichier;
	public static JLabel lbl_progres_fichier_p;
	public static JProgressBar progressBar_file;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public home(Membre membre) {
		String user_name = membre.getUsername();

		setTitle("Cloud - " + user_name);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		setLocationRelativeTo(null); // Permet de centrer le programme

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 33, 35));
		panel.setBounds(0, 0, 800, 650);
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
		 * Panel dynamique qui permet l'affichage des autres panels
		 */
		JPanel panel_dyna = new JPanel();
		panel_dyna.setBounds(210, 39, 580, 490);
		panel.add(panel_dyna);
		panel_dyna.setBackground(new Color(32, 33, 35));
		panel_dyna.setLayout(null);
		
		

		/**
		 * Intégration des panel
		 */
		JPanel[] panel_list = { new Home_panel(), new Files_panel(), new Share_panel(),
				new Archives_panel(), new Account_panel() };

		for (JPanel _panel : panel_list) {
			_panel.setLocation(0, 0);
			panel_dyna.add(_panel);
			_panel.setVisible(false);

		}

		/**
		 * Panel Menu
		 */
		JPanel menu_panel = new JPanel();
		menu_panel.setBounds(0, 0, 205, 650);
		menu_panel.setBackground(new Color(45, 45, 45));
		panel.add(menu_panel);
		menu_panel.setLayout(null);

		JLabel label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setIcon(new ImageIcon(home.class.getResource("/images/cloud.png")));
		label_3.setBounds(0, 0, 205, 146);
		menu_panel.add(label_3);
		
		lbl_progres_fichier = new JLabel("Fichier :");
		lbl_progres_fichier.setForeground(Color.WHITE);
		lbl_progres_fichier.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_fichier.setBounds(220, 540, 503, 14);
		panel.add(lbl_progres_fichier);
		
		lbl_progres_fichier_p = new JLabel("0%");
		lbl_progres_fichier_p.setForeground(Color.WHITE);
		lbl_progres_fichier_p.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_fichier_p.setBounds(750, 541, 40, 14);
		panel.add(lbl_progres_fichier_p);
		
		progressBar_file = new JProgressBar();
		progressBar_file.setForeground(new Color(0, 128, 128));
		progressBar_file.setBounds(220, 565, 560, 20);
		panel.add(progressBar_file);
		
		progressBar_total = new JProgressBar();
		progressBar_total.setForeground(new Color(0, 128, 128));
		progressBar_total.setBounds(220, 615, 560, 20);
		panel.add(progressBar_total);
		
		lbl_progres_total = new JLabel("Progression totale :");
		lbl_progres_total.setForeground(Color.WHITE);
		lbl_progres_total.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_total.setBounds(220, 590, 560, 15);
		panel.add(lbl_progres_total);

		/**
		 * Initialisation
		 */
		Menu[0] = true;
		Menu_active = panel_list[0];
		// panel_dyna.add(panel_list[0]);
		panel_list[0].setVisible(true);

		/**
		 * Mise en place des "boutons" du menu.
		 */

		// Nom de chaque menu
		String[] Menu_name = { "Accueil", "Mes fichiers", "Partag\u00E9 avec moi", "Archives",
				"Mon compte" };
		// Nom des images pour chaque menu sans extention (png seulement).
		String[] Menu_icon = { "home", "files", "share", "archive", "account" };

		int Menu_i = 0;
		for (String name : Menu_name) {
			Menu_Panel[Menu_i] = new JPanel();
			final Integer innerMenu_i = new Integer(Menu_i);

			Menu_Panel[Menu_i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// MenuReset redonne la couleur de base à tous les boutons du menu.
					MenuReset();
					// MenuClick change la couleur du bouton clické
					MenuClick(e, innerMenu_i);

					Menu_active = panel_list[innerMenu_i];
					panel_list[innerMenu_i].setVisible(true);
					panel_list[innerMenu_i].revalidate();
					
					ArrayList<Dossier> listArchive = dossierDAO.getInstance().listArchive(membre.getId());
		    		  Archives_panel.getInstance().ShowDossiers(listArchive);

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					JPanel parent = (JPanel) e.getSource();
					if (!Menu[innerMenu_i]) {
						parent.setBackground(new Color(153, 78, 44));
						parent.revalidate();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JPanel parent = (JPanel) e.getSource();
					if (!Menu[innerMenu_i]) {
						parent.setBackground(new Color(45, 45, 45));
						parent.revalidate();
					}
				}
			});
			Menu_Panel[Menu_i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Menu_Panel[Menu_i].setBounds(0, (150 + Menu_i * 45), 205, 45);

			menu_panel.add(Menu_Panel[Menu_i]);
			Menu_Panel[Menu_i].setLayout(null);
			if (Menu[innerMenu_i])
				Menu_Panel[Menu_i].setBackground(new Color(252, 129, 74));
			else
				Menu_Panel[Menu_i].setBackground(new Color(45, 45, 45));

			JLabel menu_icon = new JLabel("");
			menu_icon.setHorizontalAlignment(SwingConstants.CENTER);
			menu_icon.setIcon(new ImageIcon(home.class.getResource("/images/" + Menu_icon[Menu_i] + ".png")));
			menu_icon.setBounds(0, 0, 56, 45);
			Menu_Panel[Menu_i].add(menu_icon);

			JLabel menu_titre = new JLabel(name);
			menu_titre.setFont(new Font("Century Gothic", Font.BOLD, 14));
			menu_titre.setForeground(new Color(255, 255, 255));
			menu_titre.setBounds(66, 0, 139, 45);
			Menu_Panel[Menu_i].add(menu_titre);

			Menu_i++;
		}

		if(membreDAO.getInstance().isAdmin()) {
			JLabel lbl_Administration = new JLabel("Administration");
			lbl_Administration.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Administration.setForeground(Color.WHITE);
			lbl_Administration.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbl_Administration.setBounds(10, 612, 185, 27);
			menu_panel.add(lbl_Administration);
			
			lbl_Administration.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					admin frame = new admin();
					frame.setVisible(true);
				}
			});
		}
	}

	public void MenuClick(MouseEvent e, int nb) {
		JPanel parent = (JPanel) e.getSource();
		parent.setBackground(new Color(252, 129, 74));

		Menu_active.setVisible(false);
		Menu_active.revalidate();

		Menu[nb] = true;
	}

	public void MenuReset() {
		Color color = new Color(45, 45, 45);

		for (int i = 0; i < Menu.length; i++) {
			Menu[i] = false;
			Menu_Panel[i].setBackground(color);
		}
	}
	
	public static JPanel getMenu_active() {
		return Menu_active;
	}

	public void setMenu_active(JPanel menu_active) {
		Menu_active = menu_active;
	}
}
