package drive.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.DatatypeConverter;

public class loginDAO extends genericDAO {
	
	private final static loginDAO INSTANCE = new loginDAO();
	
	public boolean login(String username, String password) throws Exception {
		
		Connection conn = connexionBDD();
//		Statement statement;
		try {
//			statement = conn.createStatement();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
//			System.out.println("pw : " + password);
			
			PreparedStatement prep1 = conn.prepareStatement("SELECT id_me FROM membre WHERE username = ? AND password = ?");
			
			prep1.setString(1, username);
			prep1.setString(2, password);
			
			ResultSet rs = prep1.executeQuery();
			
			if(rs.first()) {
				return true;
			}else {
				return false;
			}
			
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}
	
	public static loginDAO getInstance() {
		return INSTANCE;
	}

}
