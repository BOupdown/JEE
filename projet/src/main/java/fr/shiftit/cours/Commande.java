package fr.shiftit.cours;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Commande {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@OneToMany(mappedBy="commande")
	private List<CommandeLigne> commandeLignes;
	
	@ManyToOne
	private Utilisateur utilisateur;
	
}
