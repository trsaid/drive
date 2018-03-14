package drive.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import drive.pojo.Membre;

public class membreDAO extends genericDAO {
	private final static membreDAO INSTANCE = new membreDAO();

	public List<Membre> afficherMembres() {
		ArrayList<Membre> listeMembres = new ArrayList<Membre>();
		Connection conn = connexionBDD();
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id_me, username, password FROM membre");
			while (rs.next()) {
				Membre membre = new Membre();
				membre.setId(rs.getInt(1));
				membre.setUsername(rs.getString(2));
				membre.setPassword(rs.getString(3));
				listeMembres.add(membre);
//				System.out.println("tab : " + listeMembres.get(0).getUsername());
			}
			return listeMembres;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}
	
	
	//rend la class sattique et retourne un seul resultat
	public static membreDAO getInstance() {
		return INSTANCE;
	}
}
