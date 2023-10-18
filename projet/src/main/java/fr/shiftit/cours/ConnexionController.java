package fr.shiftit.cours;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConnexionController {

	
	@GetMapping(path = "/connexion")
	public String connexion(Model model) {
	
		return "connexion";
	}
	
    @PostMapping(path= "/connexion")
    public String processLogin() {
        // Logique d'authentification ici
        return "redirect:/index";
    }
	
}
