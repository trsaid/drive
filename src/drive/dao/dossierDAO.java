package drive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			PreparedStatement prep1 = conn.prepareStatement("SELECT id, nom_dossier, date_upload FROM dossier WHERE id_utilisateurs = ?");
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

			PreparedStatement prep1 = conn.prepareStatement("SELECT id, nom_fichier, date_upload FROM fichier WHERE id_dossier = ?");
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

	public static dossierDAO getInstance() {
		return INSTANCE;
	}

}
