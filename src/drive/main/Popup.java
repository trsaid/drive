package drive.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import drive.dao.dossierDAO;
import drive.pojo.Dossier;
import drive.pojo.Fichier;
import drive.pojo.Fonction;
import drive.pojo.MyFTP;

public class Popup extends JPopupMenu {
	JMenuItem Menu1;
	JMenuItem Menu2;
	JMenuItem Menu3;
	JMenuItem Menu4;
	JLabel titre;
	
	int id_user = Main.getUser_logged().getId();

	public Popup(Dossier dossier) {
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  String Pressed = evt.getActionCommand();
		    	  if (Pressed.equals("Archiver")){
		    		  
		    		  dossierDAO.getInstance().archiverDos(dossier);
		    		  
		    		  Files_panel.getInstance().refreshDir();
		    		  
		    		  ArrayList<Dossier> listArchive = dossierDAO.getInstance().listArchive(id_user);
		    		  Archives_panel.getInstance().ShowDossiers(listArchive);
		    		  
		    	  }else if (Pressed.equals("Télécharger")) {
		    		  ArrayList<Fichier> listFichier = dossierDAO.getInstance().listFichier(dossier.getId());
		    		  Home_panel.addLog("Téléchargement du dossier " + dossier.getNom() + "...");
		    		  for (Fichier fichier : listFichier) {
						
		    			  MyFTP.download(dossier, fichier);
					}
		    	  }else if (Pressed.equals("Renommer")) {
		    		  String name = Fonction.renameDialog(dossier.getNom());
		    		  MyFTP.rename(dossier, name);
		    		  Files_panel.getInstance().refreshDir();
		    	  }else if (Pressed.equals("Partager")) {
		    		  Fonction.shareDialog();
		    	  }
		        
		      }
		    };
		
		titre = new JLabel(dossier.getNom());
		titre.setAlignmentX(CENTER_ALIGNMENT);
		add(titre);
		addSeparator();
		
		Menu1 = new JMenuItem("Télécharger");
		Menu1.setIcon(new ImageIcon(Popup.class.getResource("/images/Download_25px.png")));
		add(Menu1);
		Menu1.addActionListener(menuListener);
		
		Menu2 = new JMenuItem("Partager");
		Menu2.setIcon(new ImageIcon(Popup.class.getResource("/images/Share_25px.png")));
		add(Menu2);
		Menu2.addActionListener(menuListener);
		
		Menu3 = new JMenuItem("Renommer");
		Menu3.setIcon(new ImageIcon(Popup.class.getResource("/images/Rename_25px.png")));
		add(Menu3);
		Menu3.addActionListener(menuListener);
		
		Menu4 = new JMenuItem("Archiver");
		Menu4.setIcon(new ImageIcon(Popup.class.getResource("/images/Trash_25px.png")));
		add(Menu4);
		Menu4.addActionListener(menuListener);

	}
}
