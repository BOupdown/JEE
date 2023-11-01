package fr.shiftit.cours;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdministateurController {
	
	
	@GetMapping(path = "/Administrateur")
	public String pageAdmin(Model model, HttpSession session) {
		
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
				return "pageAdmin";
				
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


        
    }
		
		
	}

