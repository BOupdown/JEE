package fr.shiftit.cours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/produit")
public class ProduitController {
    
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/{NomProduit}")
    public String getProduit(@PathVariable String NomProduit, Model model) {
        // Utilisez le nom du produit pour récupérer les détails du produit depuis la base de données
        // et ajoutez-les au modèle pour l'affichage
        Produit produit = produitRepository.findByNom(NomProduit).orElse(null);
        model.addAttribute("produit",produit);
        
        // Redirigez vers la page de détails du produit
        return "produit";
    }
}