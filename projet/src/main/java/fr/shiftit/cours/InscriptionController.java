package fr.shiftit.cours;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class InscriptionController {
	
	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	@Autowired
    private CommandeRepository commandeRepository ;

	
	
	@GetMapping(path = "/inscription")
	public String inscription(Model model,HttpSession session) {
		
		if(session.getAttribute("user")!=null) {
			
			return"redirect:/index";
			
		}
	
		return "inscription";
	}
	
    @PostMapping(path= "/inscription")
    public String processInscription(Model model,@RequestParam String username, @RequestParam String password) {
        
        Utilisateur existingUser = utilisateurRepository.findByUsername(username).orElse(null);
        //On récupère le user à l'aide du username

        if (existingUser != null) {
        	//S'il est pas nul, problème, retourne à l'inscription
        	model.addAttribute("error", "Le nom d'utilisateur existe déjà. Veuillez en choisir un autre.");
        	return "inscription";
        } else {
       
 
    	
    	//Nouveau utilisateur crée
        Utilisateur utilisateur = new Utilisateur();
        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        utilisateur.setUsername(username);
        utilisateur.setPassword(password);
        commandeRepository.save(commande);
        utilisateurRepository.save(utilisateur);
        return "redirect:/connexion";
    }
    }
	
    }
