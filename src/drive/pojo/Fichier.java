package drive.pojo;

import java.util.Date;

public class Fichier {
	private Integer id;
	private String nom;
	private Date date;
	private String type;
	private Integer idDossier;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getIdDossier() {
		return idDossier;
	}
	public void setIdDossier(Integer idDossier) {
		this.idDossier = idDossier;
	}

}