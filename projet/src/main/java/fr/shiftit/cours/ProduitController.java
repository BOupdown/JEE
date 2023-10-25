package fr.shiftit.cours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/produit")
public class ProduitController {
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private AvisRepository avisRepository;
    
    

    @GetMapping("/{NomProduit}")
    public String getProduit(@PathVariable String NomProduit, Model model) {

        Produit produit = produitRepository.findByNom(NomProduit).orElse(null);
        model.addAttribute("produit",produit);
        

        return "produit";
    }
    

        @PostMapping("/deposer-avis")
        public String deposerAvis(@RequestParam("produitId") Long produitId,
                @RequestParam("description") String description,
                @RequestParam("note") Float note) {

            Produit produit = produitRepository.findById(produitId).orElse(null);
            Avis avis = new Avis();
            

            avis.setDescription(description);
            avis.setNote(note);
            avis.setProduit(produit);
            
            avisRepository.save(avis);
            

          return "redirect:/produit/" + produit.getNom();
        }
    
}