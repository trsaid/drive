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

import drive.main.Main;
import drive.pojo.Fonction;
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
				+ "(username, nom, prenom, password, email) "
				+ "VALUES(?,?,?,?,?)";

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
	
	public Membre membreInfo(String username) {
		Connection conn = null;
		PreparedStatement statement = null;
		String Querry = "SELECT id, username, nom, prenom, password, email "
				+ " FROM utilisateurs "
				+ "WHERE username = ?";
		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);
			
			statement.setString(1, username);
			
			ResultSet rs = statement.executeQuery();
			
			rs.next();
			
			Membre membre = new Membre();
			membre.setId(rs.getInt(1));
			membre.setUsername(rs.getString(2));
			membre.setLName(rs.getString(3));
			membre.setFName(rs.getString(4));
			membre.setPassword(rs.getString(5));
			membre.setEmail(rs.getString(6));
			
			return membre;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			
			return null;
		}
		
	}
	/**
	 * Permet de changer le nom de l'utilisateur
	 * @param membre (Le membre actuellement connecté)
	 * @param name   (Le nouveau nom)
	 */
	
	public void UpdateLname(Membre membre, String name) {
		Connection conn = null;
		PreparedStatement statement = null;
		String Querry = "UPDATE utilisateurs "
				+ " SET nom = ? "
				+ "WHERE username = ?";
		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);
			
			statement.setString(1, name);
			statement.setString(2, membre.getUsername());
			
			statement.executeUpdate();
			
			Main.getUser_logged().setLName(name);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Permet de changer le prénom de l'utilisateur
	 * @param membre (Le membre actuellement connecté)
	 * @param prenom   (Le nouveau prénom)
	 */
	public void UpdateFname(Membre membre, String prenom) {
		Connection conn = null;
		PreparedStatement statement = null;
		String Querry = "UPDATE utilisateurs "
				+ " SET prenom = ? "
				+ "WHERE username = ?";
		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);
			
			statement.setString(1, prenom);
			statement.setString(2, membre.getUsername());
			
			statement.executeUpdate();
			
			Main.getUser_logged().setFName(prenom);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Permet de changer le mot de passe de l'utilisateur
	 * @param membre (Le membre actuellement connecté)
	 * @param pass   (Le nouveau mdp)
	 */
	public void UpdatePass(Membre membre, String pass) {
		Connection conn = null;
		PreparedStatement statement = null;
		String Querry = "UPDATE utilisateurs "
				+ " SET password = ? "
				+ "WHERE username = ?";
		
		pass = Fonction.PassEncrypt(pass);
		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);
			
			statement.setString(1, pass);
			statement.setString(2, membre.getUsername());
			
			statement.executeUpdate();
			
			Main.getUser_logged().setPassword(pass);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	
	//rend la class sattique et retourne un seul resultat
	public static membreDAO getInstance() {
		return INSTANCE;
	}
}
