package fr.shiftit.cours;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeLineRepository extends JpaRepository<CommandeLigne, Long> {
	
	List<CommandeLigne> findByCommande(Commande commandeeee);

	void deleteByCommandeId(Long id);
	
}
