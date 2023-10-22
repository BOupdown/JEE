package fr.shiftit.cours;





import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, String> {

	Optional<Produit> findByNom(String nom);
}