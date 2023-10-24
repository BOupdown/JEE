package fr.shiftit.cours;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier, String>{

	Optional<Panier> findByUtilisateur(Utilisateur utilisateur);

}
