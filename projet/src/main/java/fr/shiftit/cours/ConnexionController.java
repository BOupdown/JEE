package fr.shiftit.cours;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ConnexionController {

	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@GetMapping(path = "/connexion")
	public String connexion(Model model,HttpSession session) {
		
		if(session.getAttribute("user")!=null) {
			
			return"redirect:/index";
			
		}
	
		return "connexion";
	}
	
	
	
    @PostMapping(path= "/connexion")
    public String processConnexion(Model model, @RequestParam String username, @RequestParam String password, HttpSession session) {
    	
    	Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password);
    	
    	if(utilisateur.isEmpty()) {
    		model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
    		return "connexion";
    	}
    	session.setAttribute("user", utilisateur.get());
    	
    	//session.invalidate()
        // Logique d'authentification ici
        return "redirect:/index";
    }
	
}
