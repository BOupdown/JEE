package fr.shiftit.cours;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CommandeLigne {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long qte;
	
	@ManyToOne
	private Produit produit;
	
	@ManyToOne
	private Commande commande;
	
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQte() {
		return qte;
	}

	public void setQte(Long qte) {
		this.qte = qte;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commande, produit, qte);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandeLigne other = (CommandeLigne) obj;
		return Objects.equals(commande, other.commande) && Objects.equals(produit, other.produit)
				&& Objects.equals(qte, other.qte);
	}
	
	
	
	
}
