package fr.shiftit.cours;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
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
	
	@OneToMany(mappedBy="commande",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CommandeLigne> commandeLignes;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Utilisateur utilisateur;

	

	@Override
	public int hashCode() {
		return Objects.hash(commandeLignes, utilisateur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		return Objects.equals(commandeLignes, other.commandeLignes)
				&& Objects.equals(utilisateur, other.utilisateur);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CommandeLigne> getCommandeLignes() {
		return commandeLignes;
	}

	public void setCommandeLignes(List<CommandeLigne> commandeLignes) {
		this.commandeLignes = commandeLignes;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	
}
