package fr.shiftit.cours;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByUtilisateur(Utilisateur utilisateur);
    void deleteById(Long id);


}
