package drive.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import drive.dao.dossierDAO;
import drive.dao.membreDAO;
import drive.pojo.Dossier;
import drive.pojo.Fichier;
import drive.pojo.Fonction;
import drive.pojo.MyFTP;

public class Popup extends JPopupMenu {
	JMenuItem Menu1;
	JMenuItem Menu2;
	JMenuItem Menu3;
	JMenuItem Menu4;
	JMenuItem Menu5;
	JLabel titre;
	
	int id_user = Main.getUser_logged().getId();

	public Popup(Dossier dossier) {
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  String Pressed = evt.getActionCommand();
		    	  
		    	  String msg = "";
		    	  
		    	  if (Pressed.equals("Archiver")){
		    		  
		    		  dossierDAO.getInstance().archiverDos(dossier);
		    		  
		    		  Files_panel.getInstance().refreshDir();
		    		  
		    		  Archives_panel.getInstance().refreshDir();
		    		  
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
		    		  
		    		  String email = Fonction.shareDialog();
		    		  int shareId = membreDAO.getInstance().getID(email);
		    		  int logedUser = Main.getUser_logged().getId();
		    		  
		    		  if (shareId == logedUser) {
		    			  msg = "Vous ne pouvez pas partager un dossier avec vous même.";
		    			  JOptionPane.showMessageDialog(null, msg, "Partage de dossier", JOptionPane.INFORMATION_MESSAGE);
		    			  Home_panel.addLog(msg);
		    		  }
		    		  else if(shareId > 0) {
		    			  dossierDAO.getInstance().Share(dossier, shareId);
		    			  msg = "Le dossier " + dossier.getNom() + " a été partager avec " + email;
		    			  JOptionPane.showMessageDialog(null, msg, "Partage de dossier", JOptionPane.INFORMATION_MESSAGE);
		    			  Home_panel.addLog(msg);
		    		  }
		    		  else {
		    			  msg = "Aucun utilisateur trouvé avec l'adresse : " + email;
		    			  JOptionPane.showMessageDialog(null, msg, "Partage de dossier", JOptionPane.INFORMATION_MESSAGE);
		    			  Home_panel.addLog(msg);
		    		  }
		    	  }else if (Pressed.equals("Supprimer")) {
		    		  MyFTP.delete(dossier);
		    	  }
		        
		      }
		    };
		
		titre = new JLabel(dossier.getNom());
		titre.setAlignmentX(CENTER_ALIGNMENT);
		add(titre);
		addSeparator();
		
		Menu1 = new JMenuItem("Télécharger");
		Menu1.setIcon(new ImageIcon(Popup.class.getResource("/images/Download_25px.png")));
		
		Menu1.addActionListener(menuListener);
		
		Menu2 = new JMenuItem("Partager");
		Menu2.setIcon(new ImageIcon(Popup.class.getResource("/images/Share_25px.png")));
		
		Menu2.addActionListener(menuListener);
		
		Menu3 = new JMenuItem("Renommer");
		Menu3.setIcon(new ImageIcon(Popup.class.getResource("/images/Rename_25px.png")));
		Menu3.addActionListener(menuListener);
		
		Menu4 = new JMenuItem("Archiver");
		Menu4.setIcon(new ImageIcon(Popup.class.getResource("/images/Trash_25px.png")));
		Menu4.addActionListener(menuListener);
		
		Menu5 = new JMenuItem("Supprimer");
		Menu5.setIcon(new ImageIcon(Popup.class.getResource("/images/Trash_25px.png")));
		Menu5.addActionListener(menuListener);
		
		String Menu_active = home.getMenu_active().getClass().getName();
		
		if(Menu_active.equals("drive.main.Files_panel")) {
			add(Menu1);
			add(Menu2);
			add(Menu3);
			add(Menu4);
		}else if(Menu_active.equals("drive.main.Share_panel")) {
			add(Menu1);
		}else if(Menu_active.equals("drive.main.Archives_panel")) {
			add(Menu1);
			add(Menu5);
		}

	}
}
