package drive.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import drive.pojo.FTPUpload;
import drive.pojo.Progress_Update;

@SuppressWarnings("serial")
public class Upload_panel extends JPanel {

	private JLabel lbl_upload_txt;
	public static JLabel lbl_progres_fichier;
	public static JLabel lbl_progres_total;
	private JLabel lbl_progres_fichier_p;
	
	public static JProgressBar progressBar_total;
	public static JProgressBar progressBar_file;
	
	public static String fileName;
	public static int upload_file_size;
	public static int fileNumber;

	/**
	 * Create the panel.
	 */
	public Upload_panel() {
		
		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), null));
		setBounds(10, 150, 580, 289);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		lbl_upload_txt = new JLabel("Glissez vos fichiez ici");
		lbl_upload_txt.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_upload_txt.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lbl_upload_txt.setForeground(Color.WHITE);
		lbl_upload_txt.setBounds(10, 152, 560, 50);
		add(lbl_upload_txt);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Upload_panel.class.getResource("/images/upload.png")));
		label.setBounds(208, 11, 164, 130);
		add(label);
		
		progressBar_total = new JProgressBar();
		progressBar_total.setForeground(new Color(0, 128, 128));
		progressBar_total.setBounds(10, 270, 560, 14);
		add(progressBar_total);
		
		progressBar_file = new JProgressBar();
		progressBar_file.setForeground(new Color(0, 128, 128));
		progressBar_file.setBounds(10, 225, 560, 14);
		add(progressBar_file);
		
		lbl_progres_fichier = new JLabel("Fichier :");
		lbl_progres_fichier.setForeground(new Color(255, 255, 255));
		lbl_progres_fichier.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_fichier.setBounds(10, 200, 503, 14);
		add(lbl_progres_fichier);
		
		lbl_progres_total = new JLabel("Progression totale :");
		lbl_progres_total.setForeground(Color.WHITE);
		lbl_progres_total.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_total.setBounds(10, 245, 560, 15);
		add(lbl_progres_total);
		
		lbl_progres_fichier_p = new JLabel("0%");
		lbl_progres_fichier_p.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_progres_fichier_p.setForeground(Color.WHITE);
		lbl_progres_fichier_p.setBounds(540, 201, 40, 14);
		add(lbl_progres_fichier_p);
		
		modifyLabel();

	}
	
	public void modifyLabel() {
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
					
					SwingUtilities.invokeLater(new Progress_Update());
					for (File file : files) {
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
		setTransferHandler(th);
	}

}
