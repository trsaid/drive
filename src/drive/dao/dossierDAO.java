package drive.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import drive.main.Main;
import drive.pojo.Dossier;
import drive.pojo.Fichier;

public class dossierDAO extends genericDAO {

	private final static dossierDAO INSTANCE = new dossierDAO();

	/**
	 * 
	 * @param id_user
	 * @return Une liste contenant les ID et noms des dossiers d'un utilisateur
	 *
	 */
	public ArrayList<Dossier> listDossier(int id_user) {

		ArrayList<Dossier> listDossiers = new ArrayList<Dossier>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn
					.prepareStatement("SELECT id, nom_dossier, date_upload FROM dossier WHERE id_utilisateurs = ?");
			prep1.setInt(1, id_user);
			ResultSet rs = prep1.executeQuery();
			while (rs.next()) {
				Dossier dossier = new Dossier();
				dossier.setId(rs.getInt(1));
				dossier.setNom(rs.getString(2));

				listDossiers.add(dossier);
			}
			return listDossiers;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}

	public ArrayList<Fichier> listFichier(int id_dossier) {
		ArrayList<Fichier> listFichiers = new ArrayList<Fichier>();
		Connection conn = connexionBDD();
		try {

			PreparedStatement prep1 = conn
					.prepareStatement("SELECT id, nom_fichier, date_upload FROM fichier WHERE id_dossier = ?");
			prep1.setInt(1, id_dossier);
			ResultSet rs = prep1.executeQuery();
			while (rs.next()) {
				Fichier fichier = new Fichier();
				fichier.setId(rs.getInt(1));
				fichier.setNom(rs.getString(2));

				listFichiers.add(fichier);
			}
			return listFichiers;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}

	public boolean folderExist(String name) {
		Connection conn = connexionBDD();
		try {

			PreparedStatement prep1 = conn
					.prepareStatement("SELECT nom_dossier FROM dossier WHERE id_utilisateurs = ? AND nom_dossier = ?");
			prep1.setInt(1, Main.getUser_logged().getId());
			prep1.setString(2, name);
			ResultSet rs = prep1.executeQuery();
			rs.next();

			if (rs.first()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return true;
		}
	}

	public void addFolder(String name) {
		Date date_upload = new Date(Calendar.getInstance().getTime().getTime());

		Connection conn = null;
		PreparedStatement statement = null;

		String insertQuerry = "INSERT INTO dossier" + "(nom_dossier, date_upload, id_utilisateurs) VALUES" + "(?,?,?)";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(insertQuerry);

			statement.setString(1, name);
			statement.setDate(2, date_upload);
			statement.setInt(3, Main.getUser_logged().getId());
			statement.executeUpdate();

			Dossier dossier = new Dossier();
			dossier.setNom(name);
			dossier.setDate(date_upload);

		} catch (

		SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public static dossierDAO getInstance() {
		return INSTANCE;
	}

}
