package drive.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class genericDAO {

	private final static genericDAO INSTANCE = new genericDAO();

	public Connection connexionBDD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("DRIVER OK");

			String url = "jdbc:mysql://192.168.30.1:3306/cloud";
			String username = "user";
			String password = "root";

			Connection con = DriverManager.getConnection(url, username, password);
//			System.out.println("connexion effective");
			
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static genericDAO getInstance() {
		return INSTANCE;
	}
}
