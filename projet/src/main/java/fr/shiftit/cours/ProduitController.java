package fr.shiftit.cours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/produit")
public class ProduitController {
    
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/{NomProduit}")
    public String getProduit(@PathVariable String NomProduit, Model model) {

        Produit produit = produitRepository.findByNom(NomProduit).orElse(null);
        model.addAttribute("produit",produit);
        

        return "produit";
    }
    
    @Autowired
    private AvisRepository avisRepository;
    
    @PostMapping("/deposer-avis")
    public String deposerAvis(
            @RequestParam("produitId") Long produitId,
            @RequestParam("description") String description,
            @RequestParam("note") Float note,
            HttpSession session) {
        
        // Récupérez le produit à partir de l'ID
        Produit produit = produitRepository.findById(produitId).orElse(null);
        
        // Vérifiez si le produit existe
        if (produit != null) {
            // Créez un nouvel avis
            Avis avis = new Avis();
            avis.setDescription(description);
            avis.setNote(note);
            avis.setProduit(produit);
            
            

            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            avis.setUtilisateur(utilisateur);
            
            // Enregistrez l'avis dans la base de données
            avisRepository.save(avis);
            
            return "redirect:/produit/" + produit.getNom(); // Redirigez vers la page du produit
        }
        
        return "redirect:/pageProduit";
    }
}