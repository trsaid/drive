package drive.pojo;

import java.util.ArrayList;
import java.util.Date;

import drive.dao.dossierDAO;

public class Dossier {
	private Integer id;
	private String nom;
	private Date date;
	private Integer idUser;
	private ArrayList<Fichier> fileList;
	
	
	public Dossier() {
//		fileList = dossierDAO.getInstance().listFichier(id);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer id_user) {
		this.idUser = id_user;
	}
	public ArrayList<Fichier> getFileList() {
		return fileList;
	}
	public void setFileList(ArrayList<Fichier> fileList) {
		this.fileList = fileList;
	}

}