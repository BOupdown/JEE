package fr.shiftit.cours;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnexionController {

	
	@GetMapping(path = "/connexion")
	public String ioupi(Model model) {
	
		return "connexion";
	}
	
}
