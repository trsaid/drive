package drive.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import drive.pojo.Dossier;

public class Popup extends JPopupMenu {
	JMenuItem Menu1;
	JMenuItem Menu2;
	JMenuItem Menu3;
	JMenuItem Menu4;
	JLabel titre;

	public Popup(Dossier dossier) {
		titre = new JLabel(dossier.getNom());
		titre.setAlignmentX(CENTER_ALIGNMENT);
		add(titre);
		addSeparator();
		Menu1 = new JMenuItem("Télécharger");
		Menu1.setIcon(new ImageIcon(Popup.class.getResource("/images/Download_25px.png")));
		add(Menu1);
		Menu2 = new JMenuItem("Partager");
		Menu2.setIcon(new ImageIcon(Popup.class.getResource("/images/Share_25px.png")));
		add(Menu2);
		Menu3 = new JMenuItem("Renommer");
		Menu3.setIcon(new ImageIcon(Popup.class.getResource("/images/Rename_25px.png")));
		add(Menu3);
		Menu4 = new JMenuItem("Supprimer");
		Menu4.setIcon(new ImageIcon(Popup.class.getResource("/images/Trash_25px.png")));
		add(Menu4);

	}
}
