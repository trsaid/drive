package drive.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import drive.dao.dossierDAO;
import drive.dao.membreDAO;
import drive.pojo.Membre;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class admin extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					admin frame = new admin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public admin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 470);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 33, 35));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAdministration = new JLabel("Administration");
		lblAdministration.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministration.setForeground(Color.WHITE);
		lblAdministration.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAdministration.setBounds(208, 11, 137, 25);
		contentPane.add(lblAdministration);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 47, 563, 394);
		contentPane.add(scrollPane);
		
		String[] colName = {"id", "Utilisateur", "Stockage"};
		ArrayList<Membre> list = membreDAO.getInstance().afficherMembres();
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		for (Membre membre : list) {
			model.addRow(new Object[] { String.valueOf(membre.getId()), String.valueOf(membre.getUsername()), String.valueOf(dossierDAO.getInstance().stockage(membre)) });
		}
		table = new JTable(model);
		scrollPane.setViewportView(table);

	}
}
