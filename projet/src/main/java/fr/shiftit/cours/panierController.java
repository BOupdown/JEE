package fr.shiftit.cours;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
public class panierController {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ProduitRepository produitRepository; // Assuming you have a ProduitRepository

    @GetMapping(path = "/pagePanier")
    public String pagePanier(Model model, HttpSession session) {
        Object user1 = session.getAttribute("user");

        if (user1 instanceof Utilisateur) {
            Utilisateur utilisateur = (Utilisateur) user1;
            Optional<Panier> panier = panierRepository.findByUtilisateur(utilisateur);

            if (panier.isPresent()) {
                model.addAttribute("panier", panier.get());
            }
        }

        return "pagePanier";
    }

    @PostMapping(path = "/pagePanier")
    public String addToCart(@RequestParam("nom") String nom, HttpSession session) {
        Object user1 = session.getAttribute("user");

        if (user1 instanceof Utilisateur) {
            Utilisateur utilisateur = (Utilisateur) user1;
            Optional<Panier> panier = panierRepository.findByUtilisateur(utilisateur);

            if (panier.isPresent()) {
                Panier existingPanier = panier.get();
                // Assuming you have a Produit entity with a name field, fetch the product by name.
                Produit produit = produitRepository.findByNom(nom).orElse(null);


                if (produit != null) {
                    existingPanier.getProduits().add(produit);
                    panierRepository.save(existingPanier);
                }
            }
        }

        return "redirect:/pagePanier";
    }
}
