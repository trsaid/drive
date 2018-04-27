package drive.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import drive.pojo.Membre;

public class membreDAO extends genericDAO {
	private final static membreDAO INSTANCE = new membreDAO();

	public List<Membre> afficherMembres() {
		ArrayList<Membre> listeMembres = new ArrayList<Membre>();
		Connection conn = connexionBDD();
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, username, password FROM utilisateurs");
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
	
	public boolean userExist(String username) {
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement("SELECT username FROM utilisateurs WHERE username = ?");
			prep1.setString(1, username);
			ResultSet rs = prep1.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
		
	}
	
	public void register(String username, String lName, String fName, String email, String password) throws Exception{
		Connection conn = null;
		PreparedStatement statement = null;
		String insertQuerry = "INSERT INTO utilisateurs"
				+ "(username, nom, prenom, password, email) VALUES"
				+ "(?,?,?,?,?)";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
			
			conn = connexionBDD();
			statement = conn.prepareStatement(insertQuerry);
			
			statement.setString(1, username);
			statement.setString(2, lName);
			statement.setString(3, fName);
			statement.setString(4, password);
			statement.setString(5, email);
			statement.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	
	//rend la class sattique et retourne un seul resultat
	public static membreDAO getInstance() {
		return INSTANCE;
	}
}
