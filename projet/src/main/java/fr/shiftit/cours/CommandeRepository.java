package fr.shiftit.cours;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    Commande findByUtilisateur(Utilisateur utilisateur);


}
