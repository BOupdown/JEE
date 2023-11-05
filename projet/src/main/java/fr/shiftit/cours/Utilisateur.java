package fr.shiftit.cours;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	
	private Boolean admin;
	
	@OneToMany(mappedBy="utilisateur",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Commande> commande;
	
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Avis> avis;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getAdmin() {
		return admin;
	}


	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}


	public List<Commande> getCommande() {
		return commande;
	}


	public void setCommande(List<Commande> commande) {
		this.commande = commande;
	}


	public List<Avis> getAvis() {
		return avis;
	}


	public void setAvis(List<Avis> avis) {
		this.avis = avis;
	}


	@Override
	public int hashCode() {
		return Objects.hash(admin, avis, commande, id, password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(admin, other.admin) && Objects.equals(avis, other.avis)
				&& Objects.equals(commande, other.commande) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", username=" + username + ", password=" + password + ", admin=" + admin
				+ ", commande=" + commande + ", avis=" + avis + "]";
	}

	
}
