
package fr.shiftit.cours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



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

        
        List<Avis> avis = avisRepository.findByProduit(produit);
        
        
        model.addAttribute("produit",produit);
        model.addAttribute("avis",avis);


        return "produit";
    }
    

        @PostMapping("/deposer-avis")
        public String deposerAvis(@RequestParam("produitId") Long produitId,
                @RequestParam("description") String description,
                @RequestParam("note") Float note,
                HttpServletRequest request) {
        	
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            
            if (utilisateur ==null) {
            	
            	return"redirect:/connexion";
            	
            }else {

            Produit produit = produitRepository.findById(produitId).orElse(null);
            Avis avis = new Avis();
            

            avis.setDescription(description);
            avis.setNote(note);
            avis.setProduit(produit);
            avis.setUtilisateur(utilisateur);
            
            avisRepository.save(avis);
        

          return "redirect:/produit/" + produit.getNom();
        }
}
    
}