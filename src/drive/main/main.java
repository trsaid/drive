package drive.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import drive.dao.membreDAO;
import drive.pojo.Membre;
import drive.main.Login;

public class main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		List<Membre> listMembres = new ArrayList<>();
		membreDAO membre = null;
		listMembres = membre.getInstance().afficherMembres();
		
		System.out.println(listMembres);
	}

}
