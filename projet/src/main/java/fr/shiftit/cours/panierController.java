package fr.shiftit.cours;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@Controller
public class panierController {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ProduitRepository produitRepository; 
    

    @Autowired
    private UtilisateurRepository utilisateurRepository; 


    @GetMapping(path = "/pagePanier")
    public String pagePanier(Model model, HttpSession session) {
        
        if (session.getAttribute("user") == null) {
            return "connexion";
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        if (utilisateur != null) {
        	Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByUsername(utilisateur.getUsername());
            Panier panier = utilisateurOptional.get().getPanier();

            if (panier != null) {
                model.addAttribute("panier", panier);
            }
        }

        return "pagePanier";
    }

    @Transactional
    @PostMapping("/ajouter-au-panier")
    public String ajouterAuPanier(@RequestParam("nomProduit") String nomProduit, HttpSession session, Model model) {
        Utilisateur utilisateurBis = (Utilisateur) session.getAttribute("user");

        if (utilisateurBis != null) {
            // Vous avez déjà l'objet Utilisateur, inutile d'accéder au champ username séparément.

            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByUsername(utilisateurBis.getUsername());

            if (utilisateurOptional.isPresent()) {
            	Utilisateur utilisateur = utilisateurOptional.get();
                Produit produit = produitRepository.findByNom(nomProduit).orElse(null);

                if (produit == null) {
                    return "redirect:/pageProduits";
                }

                Optional<Panier> panierOptionnal = panierRepository.findByUtilisateur(utilisateur);
                Panier panier = panierOptionnal.get();

                // Ajoutez le produit au panier
                panier.getProduits().add(produit);

                // Calculez le prix total du panier (vous devrez ajouter votre propre logique de calcul)
                Float prixTotal = calculerPrixTotal(panier);

                // Mettez à jour le prix total du panier
                panier.setPrix(prixTotal);

                // Enregistrez le panier en base de données (s'il est déjà enregistré dans la base)
                panierRepository.save(panier);

                model.addAttribute("panier", panier);
                return "pagePanier";
            } else {
                return "redirect:/connexion"; // L'utilisateur n'est pas connecté, redirigez-le vers la page de connexion.
            }
        } else {
            return "redirect:/connexion"; // L'utilisateur n'est pas connecté, redirigez-le vers la page de connexion.
        }
    }


    private Float calculerPrixTotal(Panier panier) {
        Float prixTotal = 0.0f;
        for (Produit produit : panier.getProduits()) {
            prixTotal += produit.getPrix();
        }
        return prixTotal;
    }


 

}
