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
import drive.pojo.Membre;

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
			PreparedStatement prep1 = conn.prepareStatement(
					"SELECT id, nom_dossier, date_upload FROM dossier WHERE id_utilisateurs = ?  AND id NOT IN (SELECT id_dossier FROM archives)");
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

	/**
	 * 
	 * @param id_dossier
	 * @return Renvoie la liste de fichiers contenu dans d'un dossier
	 *
	 */
	public ArrayList<Fichier> listFichier(int id_dossier) {
		ArrayList<Fichier> listFichiers = new ArrayList<Fichier>();
		Connection conn = connexionBDD();
		try {

			PreparedStatement prep1 = conn.prepareStatement(
					"SELECT id, nom_fichier, date_upload FROM fichier WHERE id_dossier = ? AND id NOT IN (SELECT id_fichier FROM est_archive)");
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

	/**
	 * 
	 * @param id_user
	 * @return Une liste contenant les ID et noms des dossiers Archivé par un
	 *         utilisateur
	 *
	 */
	public ArrayList<Dossier> listArchive(int id_user) {

		ArrayList<Dossier> listDossiers = new ArrayList<Dossier>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement(
					"SELECT A.id, nom_dos, date_archive FROM archives A INNER JOIN dossier D ON A.id_dossier = D.id WHERE id_utilisateurs = ?");
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

	/**
	 * 
	 * @param id_dossier
	 * @return Renvoie la liste de fichiers contenu dans d'un dossier
	 *
	 */
	public ArrayList<Fichier> fichierArchive(int id_dossier) {
		ArrayList<Fichier> listFichiers = new ArrayList<Fichier>();
		Connection conn = connexionBDD();
		try {

			PreparedStatement prep1 = conn.prepareStatement(
					"SELECT id_fichier, nom_fichier, date_archive FROM est_archive WHERE id_archive = ?");
			prep1.setInt(1, id_dossier);
			ResultSet rs = prep1.executeQuery();
			while (rs.next()) {
				Fichier fichier = new Fichier();
				fichier.setId(rs.getInt(1));
				fichier.setNom(rs.getString(2));
				fichier.setDate(rs.getDate(3));

				listFichiers.add(fichier);
			}
			return listFichiers;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param id_user
	 * @return Une liste contenant les ID et noms des dossiers partagé avec moi
	 *
	 */
	public ArrayList<Dossier> listShare(int id_user) {

		ArrayList<Dossier> listDossiers = new ArrayList<Dossier>();
		Connection conn = connexionBDD();
		try {
			PreparedStatement prep1 = conn.prepareStatement(
					"SELECT D.id, nom_dossier, date_upload FROM dossier D INNER JOIN droit_acces DA ON id_dossier = D.id WHERE DA.id_utilisateurs = ?");
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

	/**
	 * 
	 * @param name
	 *            = Nom du dossier
	 * @return Vérifie si l'utilisateur a un dossier dont nom est "name".
	 *
	 */
	public boolean folderExist(String name) {
		Connection conn = connexionBDD();
		try {

			PreparedStatement prep1 = conn
					.prepareStatement("SELECT nom_dossier FROM dossier WHERE id_utilisateurs = ? AND nom_dossier = ?");
			prep1.setInt(1, Main.getUser_logged().getId());
			prep1.setString(2, name);
			ResultSet rs = prep1.executeQuery();

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

	/**
	 * 
	 * @param name
	 *            = Nom du dossier
	 * @return Crée un dossier dont nom est "name".
	 *
	 */
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

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * 
	 * @param name
	 *            = Nom du dossier
	 * @return Archive le dossier dont l'id est "id".
	 *
	 */
	public void archiverDos(Dossier dossier) {
		Date date_archive = new Date(Calendar.getInstance().getTime().getTime());
		String name = dossier.getNom();
		int id = dossier.getId();

		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "SELECT id, id_dossier, nom_dos FROM archives WHERE id_dossier = ? AND nom_dos = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setInt(1, id);
			statement.setString(2, name);

			ResultSet rs = statement.executeQuery();

			if (rs.first()) {
				ArrayList<Fichier> listFichiers = getInstance().listFichier(id);
				for (Fichier fichier : listFichiers) {
					int archive = rs.getInt(1);
					if (!archivedFileExist(fichier, archive)) {
						archiveFile(fichier, archive);
					} else {
						int nbr = 0;
						while (archivedFileExist(fichier, archive)) {
							nbr++;
							fichier.setNom(newName(fichier.getNom(), nbr));
						}
						archiveFile(fichier, archive);
					}
				}

			} else {
				String Querry2 = "INSERT INTO archives (date_archive, nom_dos, id_dossier) VALUES (?, ?, ?)";
				PreparedStatement statement2 = conn.prepareStatement(Querry2);
				statement2.setDate(1, date_archive);
				statement2.setString(2, name);
				statement2.setInt(3, id);

				statement2.executeUpdate();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void archiveFile(Fichier fichier, int archive) {
		Date date_archive = new Date(Calendar.getInstance().getTime().getTime());
		String name = fichier.getNom();
		int id = fichier.getId();

		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "INSERT INTO est_archive (id_archive, id_fichier, nom_fichier, date_archive) VALUES (?, ?, ?, ?)";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setInt(1, archive);
			statement.setInt(2, id);
			statement.setString(3, name);
			statement.setDate(4, date_archive);

			statement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public boolean archivedFileExist(Fichier fichier, int id_archive) {
		String name = fichier.getNom();
		int id = fichier.getId();

		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "SELECT id_archive, nom_fichier FROM est_archive WHERE nom_fichier = ? AND id_archive = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setString(1, name);
			statement.setInt(2, id_archive);

			ResultSet rs = statement.executeQuery();

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
	
	public void Share(Dossier dossier, int id_user) {
		int id = dossier.getId();

		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "INSERT INTO droit_acces (id_utilisateurs, id_dossier) VALUES (?, ?)";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setInt(1, id_user);
			statement.setInt(2, id);

			statement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	

	public String newName(String nom, int nbr) {
		String type = "";
		String noExt = "";

		int i = nom.lastIndexOf('.');
		int p = Math.max(nom.lastIndexOf('/'), nom.lastIndexOf('\\'));

		if (i > p) {
			type = nom.substring(i + 1);
		}

		noExt = nom.substring(0, i);

		nom = noExt + "(" + nbr + ")" + "." + type;

		return nom;
	}

	public void renameFolder(Dossier dossier, String name) {
		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "UPDATE dossier SET nom_dossier = ? WHERE nom_dossier = ? AND id_utilisateurs = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setString(1, name);
			statement.setString(2, dossier.getNom());
			statement.setInt(3, Main.getUser_logged().getId());

			statement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void delete(Dossier dossier) {
		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "DELETE FROM dossier WHERE nom_dossier = ? AND id_utilisateurs = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setString(1, dossier.getNom());
			statement.setInt(2, Main.getUser_logged().getId());

			statement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public float stockage() {
		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "SELECT SUM(poids_ko) FROM fichier INNER JOIN dossier D ON D.id = id_dossier WHERE id_utilisateurs = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setInt(1, Main.getUser_logged().getId());

			ResultSet rs = statement.executeQuery();

			if (rs.first()) {
				int result = (rs.getInt(1) / 1024) / 1024;
				return result;
			} else {
				return 0;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	public float stockage(Membre membre) {
		Connection conn = null;
		PreparedStatement statement = null;

		String Querry = "SELECT SUM(poids_ko) FROM fichier INNER JOIN dossier D ON D.id = id_dossier WHERE id_utilisateurs = ?";

		try {
			conn = connexionBDD();
			statement = conn.prepareStatement(Querry);

			statement.setInt(1, membre.getId());

			ResultSet rs = statement.executeQuery();

			if (rs.first()) {
				int result = (rs.getInt(1) / 1024) / 1024;
				return result;
			} else {
				return 0;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}

	public static dossierDAO getInstance() {
		return INSTANCE;
	}

}
