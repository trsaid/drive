package drive.main;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import drive.dao.dossierDAO;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Home_panel extends JPanel {
	private JTextField Path;
	public static JProgressBar progressBar;
	static JTextArea log = new JTextArea(30, 30);
	final JScrollPane scroll = new JScrollPane(log);
	Preferences pref = Preferences.userNodeForPackage(Main.class);

	/**
	 * Create the panel.
	 */
	public Home_panel() {
		setBounds(10, 150, 580, 480);
		setBackground(new Color(32, 33, 35));
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Accueil");
		lblTitle.setBounds(224, 11, 132, 25);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setForeground(new Color(255, 255, 255));
		add(lblTitle);
		
		JButton btnParcourir = new JButton("Parcourir");
		btnParcourir.setBounds(420, 85, 150, 40);
		btnParcourir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(BrowseFile());
			}
		});
		btnParcourir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnParcourir.setBackground(new Color(252, 129, 74));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnParcourir.setBackground(new Color(153, 78, 44));
			}
		});
		btnParcourir.setForeground(Color.WHITE);
		btnParcourir.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnParcourir.setFocusable(false);
		btnParcourir.setBorderPainted(false);
		btnParcourir.setBackground(new Color(153, 78, 44));
		add(btnParcourir);
		
		Path = new JTextField();
		Path.setBounds(10, 85, 400, 40);
		Path.setEditable(false);
		Path.setText(pref.get("path", null));
		Path.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(Path);
		Path.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Mon dossier Cloud");
		lblNewLabel.setBounds(10, 60, 380, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 170, 560, 33);
		progressBar.setMaximum(100);
		float Mo = dossierDAO.getInstance().stockage();
		float pourcent = ((Mo / 200) * 100);
		System.out.println(pourcent);
		progressBar.setValue(Math.round(pourcent));
		add(progressBar);
		
		JLabel lblCapacitDeStockage = new JLabel("Capacit\u00E9 de stockage");
		lblCapacitDeStockage.setBounds(10, 145, 164, 25);
		lblCapacitDeStockage.setForeground(Color.WHITE);
		lblCapacitDeStockage.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(lblCapacitDeStockage);
		
		
		JLabel lblHistoriqueDesActions = new JLabel("Historique des actions");
		lblHistoriqueDesActions.setBounds(10, 223, 380, 25);
		lblHistoriqueDesActions.setForeground(Color.WHITE);
		lblHistoriqueDesActions.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(lblHistoriqueDesActions);
		
		JLabel lbl_stock = new JLabel("0 Mo / 200 Mo utilis\u00E9");
		lbl_stock.setBounds(192, 145, 218, 25);
		lbl_stock.setForeground(Color.WHITE);
		lbl_stock.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lbl_stock);
		lbl_stock.setText(Mo + " Mo / 200 Mo utilis\u00E9");
		scroll.setAutoscrolls(true);
		scroll.setBounds(10, 248, 560, 221);
		
		add(scroll);
		
		
		scroll.setColumnHeaderView(log);

	}
	
	public File BrowseFile() {
		File dir = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
		
		Integer opt = fileChooser.showSaveDialog(this);
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			dir = fileChooser.getSelectedFile();
			Path.setText(dir.toString());
			
			pref.put("path", dir.toString());
			
			Main.setDlPath(dir);
		}
		
		return dir;
	}
	
	public static void addLog(String msg) {
		Date date = new Date();
		String dateFormat = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		String currentText = log.getText();
        String newText = " [" + dateFormat + "] " +  msg;
        String textFinal = newText + "\n" + currentText;
        if(!msg.isEmpty())
        	log.setText(textFinal);
	}
}
