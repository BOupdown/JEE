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
	public String connexion(Model model) {
	
		return "connexion";
	}
	
	
	
    @PostMapping(path= "/connexion")
    public String processConnexion(@RequestParam String username, @RequestParam String password, HttpSession session) {
    	
    	Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password);
    	
    	if(utilisateur.isEmpty()) {
    		return "redirect:/connexion";
    	}
    	session.setAttribute("user", utilisateur.get());
    	
    	//session.invalidate()
        // Logique d'authentification ici
        return "redirect:/index";
    }
	
}
