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
	// Le repository utilisateur
	
	@GetMapping(path = "/connexion")
	public String connexion(Model model,HttpSession session) {
		
		if(session.getAttribute("user")!=null) {
			//On vérifie si l'attibut user est non null
			
			return"redirect:/index";
			
		}
	
		return "connexion";
		//redirection page connexion
	}
	
	
	
    @PostMapping(path= "/connexion")
    public String processConnexion(Model model, @RequestParam String username, @RequestParam String password, HttpSession session) {
    	
    	Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password);
    	//On récupère le user qui se connecte à l'aide de son username et son password
    	if(utilisateur.isEmpty()) {
    		model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
    		return "connexion";
    		//S'il a essayé de se connecter sans rien écrire, on réinitialise la page
    	}
    	session.setAttribute("user", utilisateur.get());
    	// On set l'attribut user de la session

        return "redirect:/index";
        //On va sur la page index
    }
	
}
