package fr.shiftit.cours;

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

	public Long getQte() {
		return qte;
	}

	public void setQte(Long qte) {
		this.qte = qte;
	}
	
	
}
