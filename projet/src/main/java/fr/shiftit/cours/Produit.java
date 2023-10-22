package fr.shiftit.cours;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produit {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nom;
	private String description;
	private Float prix;
	private String photo;
	private Long stock;
	private String categorie;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPrix() {
		return prix;
	}
	public void setPrix(Float prix) {
		this.prix = prix;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(categorie, description, nom, photo, prix, stock);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		return Objects.equals(categorie, other.categorie) && Objects.equals(description, other.description)
				&& Objects.equals(nom, other.nom) && Objects.equals(photo, other.photo)
				&& Objects.equals(prix, other.prix) && Objects.equals(stock, other.stock);
	}
	
	@Override
	public String toString() {
		return "Produit [nom=" + nom + ", description=" + description + ", prix=" + prix + ", photo=" + photo
				+ ", stock=" + stock + ", categorie=" + categorie + "]";
	}
	
	
	
	
	
	
	
}
