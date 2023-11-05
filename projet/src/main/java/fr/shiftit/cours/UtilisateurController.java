package fr.shiftit.cours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Controller
public class UtilisateurController {
	
	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private CommandeRepository commandeRepository;
	
	@Autowired
	private AvisRepository avisRepository;
	
	
	
	@GetMapping(path = "/Utilisateur")
	public String pageAdminUtilisateur(Model model, HttpSession session) {
		
		if(session.getAttribute("user") != null) {
			Utilisateur user = (Utilisateur) session.getAttribute("user");
	
			List<Commande> commandes = (List<Commande>) commandeRepository.findByUtilisateur(user);
		    model.addAttribute("commandes", commandes);  
			
			
				return "pageUtilisateur";
	
		}

        
		return "redirect:/connexion";


        
    }
	
	

	
    @Transactional
	@PostMapping("/supprimer-utilisateur")
	public String supprimerProduit(HttpSession session) {
			
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
    		
    		
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
			
				avisRepository.deleteByUtilisateur(utilisateur);
				
				utilisateurRepository.deleteById(utilisateur.getId());
			    return "redirect:/deconnexion";

		}

        
		return "redirect:/connexion";


	}
    
    
    @GetMapping("/modifier-utilisateur")
    public String afficherFormulaireModification(HttpSession session) {
    	
		if(session.getAttribute("user") != null) {
			
			
			return "ModifierUtilisateur2";
			
			
			}

        
		return "redirect:/connexion";
		
    }

    @PostMapping("/modifier-utilisateur")
    public String modifierUtilisateur(HttpSession session,Model model,@RequestParam("username") String username,
			@RequestParam("password") String password) {

    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
	
		    	
		    	if(!(utilisateur.getUsername().equals(username))) {
		    		
		    	
		    		Utilisateur dejaPris = utilisateurRepository.findByUsername(username).orElse(null);
		    		
		    		if(dejaPris!=null) {
		    			
		    			model.addAttribute("ErreurModif","Erreur nom d'utilisateur déjà pris");
		    			model.addAttribute("utilisateur",utilisateur);
		    			return "ModifierUtilisateur2";
		    			
		    		}
		    		
		    	}
		    	
		    	utilisateur.setUsername(username);
		    	utilisateur.setPassword(password);
		    	
		    	utilisateurRepository.save(utilisateur);
		    	
		        return "redirect:/Utilisateur";
				
    	}
				
			

        
		return "redirect:/connexion";
    	
        

    }

}
