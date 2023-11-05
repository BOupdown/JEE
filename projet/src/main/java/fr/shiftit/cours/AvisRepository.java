package fr.shiftit.cours;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis, String> {
	
	List<Avis> findByProduit(Produit produit);
    void deleteById(Long id);
    void deleteByUtilisateur(Utilisateur utilisateur);
}

