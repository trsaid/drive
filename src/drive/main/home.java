package drive.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import drive.dao.loginDAO;
import drive.pojo.Membre;
import drive.pojo.FTPUpload;
import javax.swing.JProgressBar;

public class home extends JFrame {

	private JPanel contentPane;
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
		lblCompte.setFont(new Font("Tahoma", Font.BOLD, 16));
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

		JPanel panel_upload = new JPanel();
		panel_upload.setBounds(10, 150, 580, 289);
		panel_upload.setBackground(new Color(32, 33, 35));
		panel.add(panel_upload);

		panel_upload.add(new DropPane());
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(18, 113, 518, 23);
		panel.add(progressBar);

	}

	@SuppressWarnings("serial")
	public class DropPane extends JPanel {
		private DropTarget dropTarget;
		private DropTargetHandler dropTargetHandler;
		private Point dragPoint;

		private boolean dragOver = false;
		private BufferedImage target;

		private JLabel message;
		

		public DropPane() {
			try {
				target = ImageIO.read(new File("upload.png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			setLayout(null);
			
			
			message = new JLabel("Glissez vos fichiez ici");
			message.setFont(new Font("Tahoma", Font.BOLD, 16));
			message.setForeground(Color.WHITE);
			message.setBounds(0, 50, 580, 350);
			message.setHorizontalAlignment(SwingConstants.CENTER);
			add(message);

		}


		protected DropTarget getMyDropTarget() {
			if (dropTarget == null) {
				dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
			}
			return dropTarget;
		}

		protected DropTargetHandler getDropTargetHandler() {
			if (dropTargetHandler == null) {
				dropTargetHandler = new DropTargetHandler();
			}
			return dropTargetHandler;
		}

		@Override
		public void addNotify() {
			super.addNotify();
			try {
				getMyDropTarget().addDropTargetListener(getDropTargetHandler());
			} catch (TooManyListenersException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void removeNotify() {
			super.removeNotify();
			getMyDropTarget().removeDropTargetListener(getDropTargetHandler());
		}

		@Override
		protected void paintComponent(Graphics g) {
			setBounds(0, 0, 580, 288);
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			
			/**
			 * Image upload
			 */
			int x, y;
			if (dragOver) {
//				setBackground(new Color(255, 255, 255));
				if (dragPoint != null && target != null) {
					x = dragPoint.x - 12;
					y = dragPoint.y - 12;
					g2d.drawImage(target, x, y, this);
				}
				g2d.dispose();
				
			}
			x = getWidth()/2 - target.getWidth()/2;
			y = getHeight()/2 - target.getHeight()/2;
			g2d.drawImage(target, x, y, this);
			setBackground(new Color(32, 33, 35));
			
			
			setBorder(BorderFactory.createDashedBorder(new Color(187, 187, 187), 7, 7, 3, true));
			
		}

		protected void importFiles(final List files) {
			Runnable run = new Runnable() {
				@Override
				public void run() {
					message.setText("Vous avez ajouté " + files.size() + " fichier(s)");
					String fileName = files.get(0).toString();
					FTPUpload.Upload(fileName);
				}
			};
			SwingUtilities.invokeLater(run);
		}

		protected class DropTargetHandler implements DropTargetListener {

			protected void processDrag(DropTargetDragEvent dtde) {
				if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					dtde.acceptDrag(DnDConstants.ACTION_COPY);
				} else {
					dtde.rejectDrag();
				}
			}

			@Override
			public void dragEnter(DropTargetDragEvent dtde) {
				processDrag(dtde);
				SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
				repaint();
			}

			@Override
			public void dragOver(DropTargetDragEvent dtde) {
				processDrag(dtde);
				SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
				repaint();
			}

			@Override
			public void dropActionChanged(DropTargetDragEvent dtde) {
			}

			@Override
			public void dragExit(DropTargetEvent dte) {
				SwingUtilities.invokeLater(new DragUpdate(false, null));
				repaint();
			}

			@Override
			public void drop(DropTargetDropEvent dtde) {

				SwingUtilities.invokeLater(new DragUpdate(false, null));

				Transferable transferable = dtde.getTransferable();
				if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					dtde.acceptDrop(dtde.getDropAction());
					try {

						List transferData = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
						if (transferData != null && transferData.size() > 0) {
							importFiles(transferData);
							dtde.dropComplete(true);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					dtde.rejectDrop();
				}
			}
		}

		public class DragUpdate implements Runnable {

			private boolean dragOver;
			private Point dragPoint;

			public DragUpdate(boolean dragOver, Point dragPoint) {
				this.dragOver = dragOver;
				this.dragPoint = dragPoint;
			}

			@Override
			public void run() {
				DropPane.this.dragOver = dragOver;
				DropPane.this.dragPoint = dragPoint;
				DropPane.this.repaint();
			}
		}

	}
}
