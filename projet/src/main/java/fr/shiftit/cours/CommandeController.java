package fr.shiftit.cours;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommandeController {
	
	@Autowired
    private CommandeRepository commandeRepository ;

	
	@Autowired
    private CommandeLineRepository commandeLineRepository ;
	
	@Autowired
    private ProduitRepository produitRepository ;
	

	@GetMapping("/Panier")
    public String getCommande(Model model, HttpSession session) {


		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");

			Commande commande = commandeRepository.findByUtilisateur(user);
			List<CommandeLigne> commandeLignes = commandeLineRepository.findByCommande(commande);
			
	        model.addAttribute("commande", commande);

	        model.addAttribute("commandeLignes", commandeLignes);
	        
	        return "Panier";
		}

        
		return "redirect:/connexion";


        
    }
	
	@PostMapping("/addCommande")
    public String ajouterAuPanier(@RequestParam("produitId") Long produitId,
    		@RequestParam("nbExemplaires") Long nbExemplaires,
    		HttpSession session, Model model) {
    	
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        
        
        if (utilisateur == null) {
        	
        	return"redirect:/connexion";
        	
        } else {

        Produit produit = produitRepository.findById(produitId).orElse(null);
		Commande commande = commandeRepository.findByUtilisateur(utilisateur);
        CommandeLigne commandeLigne = new CommandeLigne();
        
        commandeLigne.setQte(nbExemplaires);
        commandeLigne.setCommande(commande);
        commandeLigne.setProduit(produit);
        
        commandeLineRepository.save(commandeLigne);
    

      return "redirect:/Panier";
    }
}
	
	@PostMapping("/suppligneCommande")
	public String supprimerLignePanier(@RequestParam("commandeLineId") Long commandeLineId,
	                               HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	        
	    } else {
	    	
	    	Optional<CommandeLigne> ligneDeCommande = commandeLineRepository.findById(commandeLineId);
	    	
	    	if (ligneDeCommande.isPresent()) {
	            commandeLineRepository.delete(ligneDeCommande.get()); // Delete the found command line
	    	}

	          

	        return "redirect:/Panier";
	    }
	}
	
	@PostMapping("/achatPanier")
	public String acheterPanier(@RequestParam("commandeId") Long commandeId,
	                           HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	        
	    } else {
	        Optional<Commande> commande = commandeRepository.findById(commandeId);

	        if (commande.isPresent()) {
	            Commande maCommande = commande.get();

	            List<CommandeLigne> commandesLignes = maCommande.getCommandeLignes();

	            for (CommandeLigne commandeLigne : commandesLignes) {
	                Produit produit = commandeLigne.getProduit();
	                Long quantite = commandeLigne.getQte();

	                // Update the stock
	                Long currentStock = produit.getStock();
	                if (currentStock >= quantite) {
	                    produit.setStock(currentStock - quantite);
	                    produitRepository.save(produit);
	                } 

	                // Remove the CommandeLigne
	                commandeLineRepository.delete(commandeLigne);
	            }
	        }

	        return "redirect:/Panier";
	    }
	}
	
	@PostMapping("/suppPanier")
	public String supprimerLePanier(@RequestParam("commandeId") Long commandeId,
	                           HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	        
	    } else {
	        Optional<Commande> commande = commandeRepository.findById(commandeId);

	        if (commande.isPresent()) {
	            Commande maCommande = commande.get();

	            List<CommandeLigne> commandesLignes = maCommande.getCommandeLignes();

	            for (CommandeLigne commandeLigne : commandesLignes) {

	                commandeLineRepository.delete(commandeLigne);
	            }
	        }

	        return "redirect:/Panier";
	    }
	}





}
