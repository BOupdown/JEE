package fr.shiftit.cours;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

public class CommandeController {
	
	@Autowired
    private CommandeRepository commandeRepository ;

	
	@Autowired
    private CommandeLineRepository commandeLineRepository ;
	
	@Autowired
    private ProduitRepository produitRepository ;
	

	@GetMapping("/addCommande")
    public String getCommande(Model model, HttpSession session) {


		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");

			Commande commande = commandeRepository.findByUtilisateur(user);
			List<CommandeLigne> commandeLignes = commandeLineRepository.findByCommande(commande);
			
	        model.addAttribute("commandeLignes", commandeLignes);
	        
	        return "addPanier";
		}

        
		return "produit";


        
    }
	
	@PostMapping("/addCommande")
    public String ajouterAuPanier(@RequestParam("produitId") Long produitId,
    		@RequestParam("nbExemplares") Long nbExemplares,
    		HttpSession session, Model model) {
    	
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        
        
        if (utilisateur == null) {
        	
        	return"redirect:/index";
        	
        } else {

        Produit produit = produitRepository.findById(produitId).orElse(null);
		Commande commande = commandeRepository.findByUtilisateur(utilisateur);
        CommandeLigne commandeLigne = new CommandeLigne();
        
        commandeLigne.setQte(nbExemplares);
        commandeLigne.setCommande(commande);
        commandeLigne.setProduit(produit);
        
        commandeLineRepository.save(commandeLigne);
    

      return "addPanier";
    }
}

}
