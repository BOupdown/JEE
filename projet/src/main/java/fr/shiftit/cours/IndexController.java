package fr.shiftit.cours;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(path = "/index")
	public String index(Model model) {
	
		return "index";
		//Redirection page index
	}
	
	

}
