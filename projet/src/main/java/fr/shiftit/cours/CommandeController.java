package fr.shiftit.cours;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
	
	@Autowired
    private UtilisateurRepository utilisateurRepository ;
	

	@GetMapping("/Panier")
	public String getCommande(Model model, HttpSession session) {
	    if (session.getAttribute("user") != null) {

	        List<CommandeLigne> commandeLignes = (List<CommandeLigne>) session.getAttribute("commandeLignes");

	        model.addAttribute("commandeLignes", commandeLignes);

	        return "Panier";
	    }

	    return "redirect:/connexion";
	}

	
	@PostMapping("/addPanier")
	public String ajouterAuPanier(@RequestParam("produitId") Long produitId,
	        @RequestParam("nbExemplaires") Long nbExemplaires,
	        HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	    } else {
	        Produit produit = produitRepository.findById(produitId).orElse(null);
	        
	        List<CommandeLigne> commandeLignes = (List<CommandeLigne>) session.getAttribute("commandeLignes");
	        
	        if (commandeLignes == null) {
	            commandeLignes = new ArrayList<>();
	        }


	        boolean produitExisteDansPanier = false;
	        for (CommandeLigne commandeLigne : commandeLignes) {
	            if (commandeLigne.getProduit().getId().equals(produitId)) {

	                Long nouvelleQuantite = commandeLigne.getQte() + nbExemplaires;
	                commandeLigne.setQte(nouvelleQuantite);
	                produitExisteDansPanier = true;
	                break;
	            }
	        }

	        if (!produitExisteDansPanier) {

	            CommandeLigne nouvelleCommandeLigne = new CommandeLigne();
	            nouvelleCommandeLigne.setQte(nbExemplaires);
	            nouvelleCommandeLigne.setProduit(produit);


	            nouvelleCommandeLigne.setPosition(commandeLignes.size() + 1);

	            commandeLignes.add(nouvelleCommandeLigne);
	        }

	        session.setAttribute("commandeLignes", commandeLignes);

	        return "redirect:/Panier";
	    }
	}


	@PostMapping("/supplignePanier")
	public String supprimerLignePanier(@RequestParam("commandeLinePosition") int commandeLinePosition,
	                                   HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	    } else {

	        List<CommandeLigne> commandeLignes = (List<CommandeLigne>) session.getAttribute("commandeLignes");

	        if (commandeLignes != null) {
	            Iterator<CommandeLigne> iterator = commandeLignes.iterator();
	            while (iterator.hasNext()) {
	                CommandeLigne ligneDeCommande = iterator.next();
	                if (ligneDeCommande.getPosition() == commandeLinePosition) {
	                    iterator.remove(); 
	                    break;
	                }
	            }

	            session.setAttribute("commandeLignes", commandeLignes);
	        }

	        return "redirect:/Panier";
	    }
	}

	@PostMapping("/Commander")
	public String commander(HttpSession session, Model model) {
	    Utilisateur utilisateur1 = (Utilisateur) session.getAttribute("user");
	    Utilisateur utilisateur = utilisateurRepository.findById(utilisateur1.getId()).orElse(null);
	    if (utilisateur == null) {
	        return "redirect:/connexion";
	    } else {
	        List<CommandeLigne> commandeLignes = (List<CommandeLigne>) session.getAttribute("commandeLignes");
	    	
	    	 for (CommandeLigne commandeLigne : commandeLignes) {
	                Produit produit = commandeLigne.getProduit();
	                Long quantite = commandeLigne.getQte();

	                Long currentStock = produit.getStock();
	                if (currentStock < quantite) {
	                	
	                	model.addAttribute("erreur", "Erreur, nos stock sont insuffisant !");
	                	 model.addAttribute("commandeLignes", commandeLignes);
	         	        return "Panier";
	                	
	                }
	    	
	    	 }
	 	
	        Commande nouvelleCommande = new Commande();
	        nouvelleCommande.setUtilisateur(utilisateur);

	        if (commandeLignes != null && !commandeLignes.isEmpty()) {
	            commandeRepository.save(nouvelleCommande);

	            for (CommandeLigne commandeLigne : commandeLignes) {
	                Produit produit = commandeLigne.getProduit();
	                Long quantite = commandeLigne.getQte();
	                Long currentStock = produit.getStock();

	                    CommandeLigne nouvelleCommandeLigne = new CommandeLigne();
	                    nouvelleCommandeLigne.setQte(quantite);
	                    nouvelleCommandeLigne.setProduit(produit);
	                    nouvelleCommandeLigne.setCommande(nouvelleCommande);
	                    commandeLineRepository.save(nouvelleCommandeLigne);


	                    produit.setStock(currentStock - quantite);
	                    produitRepository.save(produit);
	                }
	                	
	                	
	                }
	          

	            session.setAttribute("commandeLignes", new ArrayList<CommandeLigne>());
	        }

	        return "redirect:/Panier";
	    }




	@PostMapping("/suppPanier")
	public String supprimerLePanier(HttpSession session, Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

	    if (utilisateur == null) {
	        return "redirect:/connexion";
	        
	    } else {
	        

	        session.setAttribute("commandeLignes", new ArrayList<CommandeLigne>());

	        return "redirect:/Panier";
	    }
	}





}
