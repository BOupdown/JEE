package fr.shiftit.cours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Controller
@RequestMapping("/Administrateur")
public class AdminCommandeController {
	
	@Autowired
    private CommandeRepository commandeRepository;
	
	@Autowired
    private CommandeLineRepository commandeLigneRepository;
	
	
	
	
	@GetMapping(path = "/Commandes")
	public String pageAdminCommande(Model model, HttpSession session) {
		
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
			    List<Commande> commandes = commandeRepository.findAll();
			    model.addAttribute("commandes", commandes);  
				
				return "AdminCommandes";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


        
    }
	
	
	
	
	
		
	

	
    @Transactional
	@PostMapping("/supprimer-commande")
	public String supprimerCommande(HttpSession session,@RequestParam Long commandeId) {
			
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {

			    commandeLigneRepository.deleteByCommandeId(commandeId);
				commandeRepository.deleteById(commandeId);
			    return "redirect:/Administrateur/Commandes";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


	}
    
    
   

}
