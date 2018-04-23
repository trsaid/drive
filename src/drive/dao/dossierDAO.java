package drive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drive.pojo.Membre;

public class dossierDAO extends genericDAO {
	
private final static dossierDAO INSTANCE = new dossierDAO();
	
	public List<String> listDossier(int id_user) throws Exception {
		
		ArrayList<String> listDossiers = new ArrayList<String>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement("SELECT id, nom_dossier, date_upload FROM dossier WHERE id_utilisateurs = ?");
			prep1.setInt(1, id_user);
			ResultSet rs = prep1.executeQuery();
			while (rs.next()) {
//				Membre membre = new Membre();
//				membre.setId(rs.getInt(1));
//				membre.setUsername(rs.getString(2));
//				membre.setPassword(rs.getString(3));
				listDossiers.add(rs.getString(2));
//				System.out.println("tab : " + listeMembres.get(0).getUsername());
			}
			return listDossiers;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}
	
	public List<Membre> userInfo(int id) {
		ArrayList<Membre> info = new ArrayList<Membre>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement("SELECT id, username FROM utilisateurs WHERE id = ?");
			
			prep1.setInt(1, id);
			ResultSet rs = prep1.executeQuery();
			rs.next();
			
			Membre membre = new Membre();
			membre.setId(rs.getInt(1));
			membre.setUsername(rs.getString(2));
			info.add(membre);
			
			return info;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}
	
	public static dossierDAO getInstance() {
		return INSTANCE;
	}

}
