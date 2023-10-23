package fr.shiftit.cours;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

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
	
	@ManyToMany
	private List<Categorie> categories;
	
	@ManyToMany
	private List<Panier> paniers;
	
	@OneToMany
	private List<Avis> avis;

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

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public List<Panier> getPaniers() {
		return paniers;
	}

	public void setPaniers(List<Panier> paniers) {
		this.paniers = paniers;
	}

	public List<Avis> getAvis() {
		return avis;
	}

	public void setAvis(List<Avis> avis) {
		this.avis = avis;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avis, categories, description, id, nom, paniers, photo, prix, stock);
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
		return Objects.equals(avis, other.avis) && Objects.equals(categories, other.categories)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(nom, other.nom) && Objects.equals(paniers, other.paniers)
				&& Objects.equals(photo, other.photo) && Objects.equals(prix, other.prix)
				&& Objects.equals(stock, other.stock);
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + ", photo="
				+ photo + ", stock=" + stock + ", categories=" + categories + ", paniers=" + paniers + ", avis=" + avis
				+ "]";
	}
	
	

	
}
