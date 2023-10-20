package fr.shiftit.cours;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeconnexionController {
	
	@GetMapping(path = "/deconnexion")
	public String deconnexion(Model model,HttpSession session) {
		
		session.invalidate();
	
		return "redirect:/index";
	}
	
	

}
