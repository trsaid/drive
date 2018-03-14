package drive.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import drive.dao.membreDAO;
import drive.pojo.Membre;

public class main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				loginFrame login = new loginFrame();
				login.setVisible(true);
			}
		});

		List<Membre> listMembres = new ArrayList<>();
		membreDAO membre = null;
		listMembres = membre.getInstance().afficherMembres();
		
		System.out.println(listMembres);
	}

}
