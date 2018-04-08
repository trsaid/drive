package drive.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;
import drive.pojo.FTPUpload;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;

public class home extends JFrame {

	private JPanel contentPane;
	private JPanel panel_upload;
	
	private JLabel lbl_upload_txt;
	private JLabel lbl_progres_total;
	private JLabel lbl_progres_fichier;
	
	private JProgressBar progressBar_file;
	private JProgressBar progressBar_total;
	
	private String fileName;
	private int upload_file_size;
	private int fileNumber;
	
	
	private int xMouse, yMouse;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public home(int user_id) {
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

		JLabel lblCompte = new JLabel("Compte : ");
		lblCompte.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblCompte.setForeground(Color.WHITE);
		lblCompte.setBounds(54, 67, 332, 52);
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

		List<Membre> user = loginDAO.getInstance().userInfo(user_id);
		lblCompte.setText("Compte : " + user.get(0).getUsername());

		panel_upload = new JPanel();
		panel_upload.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), null));
		panel_upload.setBounds(10, 150, 580, 289);
		panel_upload.setBackground(new Color(32, 33, 35));
		panel.add(panel_upload);
		panel_upload.setLayout(null);
		
		lbl_upload_txt = new JLabel("Glissez vos fichiez ici");
		lbl_upload_txt.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_upload_txt.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lbl_upload_txt.setForeground(Color.WHITE);
		lbl_upload_txt.setBounds(10, 152, 560, 50);
		panel_upload.add(lbl_upload_txt);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\trsai\\Desktop\\java\\drive\\drive\\upload.png"));
		label.setBounds(208, 11, 164, 130);
		panel_upload.add(label);
		
		progressBar_total = new JProgressBar();
		progressBar_total.setForeground(new Color(0, 128, 128));
		progressBar_total.setBounds(10, 270, 560, 14);
		panel_upload.add(progressBar_total);
		
		progressBar_file = new JProgressBar();
		progressBar_file.setForeground(new Color(0, 128, 128));
		progressBar_file.setBounds(10, 225, 560, 14);
		panel_upload.add(progressBar_file);
		
		lbl_progres_fichier = new JLabel("Fichier :");
		lbl_progres_fichier.setForeground(new Color(255, 255, 255));
		lbl_progres_fichier.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_fichier.setBounds(10, 200, 560, 14);
		panel_upload.add(lbl_progres_fichier);
		
		lbl_progres_total = new JLabel("Progression totale :");
		lbl_progres_total.setForeground(Color.WHITE);
		lbl_progres_total.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_total.setBounds(10, 245, 560, 15);
		panel_upload.add(lbl_progres_total);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 113, 580, 23);
		panel.add(progressBar);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(501, 87, 89, 23);
		panel.add(btnNewButton);

		// ActionListener actionListener = new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		Thread stepper = new FTPUpload(progressBar);
		stepper.start();
		// }
		// };
		// btnNewButton.addActionListener(actionListener);

		modifyLabel();

	}

	public void modifyLabel() {
		@SuppressWarnings("serial")
		TransferHandler th = new TransferHandler() {

			@Override
			public boolean canImport(JComponent comp, DataFlavor[] tf) {
				return true;
			};

			@SuppressWarnings("unchecked")
			@Override
			public boolean importData(JComponent comp, Transferable t) {
				List<File> files;
				try {
					files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
					upload_file_size = files.size();
					lbl_upload_txt.setText("Vous avez envoyé " + upload_file_size + " fichier" + (upload_file_size > 1 ? "s" : ""));
					
					for (File file : files) {
						SwingUtilities.invokeLater(new Progress_Update());
						fileNumber = files.indexOf(file) + 1;
						fileName = file.getName();
						String filePath = file.toString();
						System.out.println("Envoi du fichier " + fileNumber + "/" + upload_file_size);
						
						FTPUpload.Upload(filePath);
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		};
		panel_upload.setTransferHandler(th);
	}
	public class Progress_Update extends Thread {
		private static final int DELAY = 200;
		@Override
		public void run() {
			try {
				progressBar_total.setMaximum(upload_file_size);
				progressBar_total.setValue(fileNumber);
				lbl_progres_fichier.setText("Fichier : " + fileName);
				lbl_progres_total.setText("Progression totale : " + fileNumber + "/" + upload_file_size);
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
