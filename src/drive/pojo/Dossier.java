package drive.pojo;

import java.util.Date;

public class Dossier {
	private Integer id;
	private String nom;
	private Date date;
	private Integer idUser;
	
	
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

}