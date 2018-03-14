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

public class loginDAO extends genericDAO {
	
	private final static loginDAO INSTANCE = new loginDAO();
	
	public int login(String username, String password) throws Exception {
		
		Connection conn = connexionBDD();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
			
			PreparedStatement prep1 = conn.prepareStatement("SELECT id_me FROM membre WHERE username = ? AND password = ?");
			
			prep1.setString(1, username);
			prep1.setString(2, password);
			
			ResultSet rs = prep1.executeQuery();
			rs.next();
			
			int user_id = rs.getInt(1);
			
			if(rs.first()) {
				return user_id;
			}else {
				return 0;
			}
			
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	public List<Membre> userInfo(int id) {
		ArrayList<Membre> info = new ArrayList<Membre>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement("SELECT id_me, username FROM membre WHERE id_me = ?");
			
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
	
	public static loginDAO getInstance() {
		return INSTANCE;
	}

}
