package fr.shiftit.cours;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, String> {


}