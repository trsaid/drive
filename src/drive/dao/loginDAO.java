package drive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import drive.pojo.Fonction;
import drive.pojo.Membre;

public class loginDAO extends genericDAO {

	private final static loginDAO INSTANCE = new loginDAO();

	public Membre login(String username, String password) throws Exception {
		
		Connection conn = null;
		PreparedStatement statement = null;
		String Querry = "SELECT id FROM utilisateurs WHERE username = ? AND password = ?";
		password = Fonction.PassEncrypt(password);

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			rs.next();

			if (rs.first()) {
				return membreDAO.getInstance().membreInfo(username);
			} else {
				return null;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}

	public static loginDAO getInstance() {
		return INSTANCE;
	}

}
