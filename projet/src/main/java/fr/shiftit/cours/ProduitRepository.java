package fr.shiftit.cours;





import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, String> {

	Optional<Produit> findById(Long id);
	Optional<Produit> findByNom(String NompProduit);
}