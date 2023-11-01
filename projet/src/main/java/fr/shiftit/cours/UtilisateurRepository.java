package fr.shiftit.cours;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

	Optional<Utilisateur> findByUsernameAndPassword(String username,String password);
	Optional<Utilisateur> findByUsername(String username);


}