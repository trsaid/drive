package drive.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.DatatypeConverter;

public class loginDAO extends genericDAO {
	
	public boolean login(String username, String password) throws Exception {
		
		Connection conn = connexionBDD();
		Statement statement;
		try {
			statement = conn.createStatement();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
			System.out.println("pw : " + password);

			ResultSet rs = statement.executeQuery("SELECT id_me FROM membre WHERE username="+username+" AND password="+password+"");
			rs.last();
			if(rs.getRow() > 0) {
				return true;
			}else {
				return false;
			}
			
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
