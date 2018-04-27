package drive.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Calendar;

public class uploadDAO extends genericDAO {

	private final static uploadDAO INSTANCE = new uploadDAO();

	public void uploadFile(File file, int dossier) {
		
		String nom = file.getName();
		long poid = file.length();
		Date date_upload = new Date(Calendar.getInstance().getTime().getTime());
		String type = "";

		int i = nom.lastIndexOf('.');
		int p = Math.max(nom.lastIndexOf('/'), nom.lastIndexOf('\\'));

		if (i > p) {
			type = nom.substring(i+1);
		}
		
		Connection conn = null;
		PreparedStatement statement = null;

		String insertQuerry = "INSERT INTO fichier"
				+ "(nom_fichier, poids_ko, date_upload, type, id_dossier) VALUES"
				+ "(?,?,?,?,?)";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(insertQuerry);

			statement.setString(1, nom);
			statement.setLong(2, poid);
			statement.setDate(3, date_upload);
			statement.setString(4, type);
			statement.setInt(5, dossier);
			statement.executeUpdate();

		} catch (

		SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// rend la class sattique et retourne un seul resultat
	public static uploadDAO getInstance() {
		return INSTANCE;
	}

}
