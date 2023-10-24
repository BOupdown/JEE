package fr.shiftit.cours;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Panier {
	

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Float prix;
	private Integer quantite;
	
	@OneToOne(mappedBy = "panier")

	private Utilisateur utilisateur;
	
	@ManyToMany(mappedBy="paniers")
	private List<Produit> produits;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, prix, produits, quantite, utilisateur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Panier other = (Panier) obj;
		return Objects.equals(id, other.id) && Objects.equals(prix, other.prix)
				&& Objects.equals(produits, other.produits) && Objects.equals(quantite, other.quantite)
				&& Objects.equals(utilisateur, other.utilisateur);
	}

	@Override
	public String toString() {
		return "Panier [id=" + id + ", prix=" + prix + ", quantite=" + quantite + ", utilisateur=" + utilisateur
				+ ", produits=" + produits + "]";
	}

	
	
}
