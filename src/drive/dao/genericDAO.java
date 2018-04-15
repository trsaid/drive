package drive.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class genericDAO {

	private final static genericDAO INSTANCE = new genericDAO();

	public Connection connexionBDD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("DRIVER OK");

			String url = "jdbc:mysql://localhost:3306/drive";
			String username = "site";
			String password = "said";

			Connection con = DriverManager.getConnection(url, username, password);
			System.out.println("connexion effective");
			
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
