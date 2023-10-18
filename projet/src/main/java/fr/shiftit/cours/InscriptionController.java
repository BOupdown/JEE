package fr.shiftit.cours;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InscriptionController {
	
	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	@GetMapping(path = "/inscription")
	public String inscription(Model model) {
	
		return "inscription";
	}
	
    @PostMapping(path= "/inscription")
    public String processInscription(@RequestParam String username, @RequestParam String password) {
        
        Utilisateur existingUser = utilisateurRepository.findById(username).orElse(null);

        if (existingUser != null) {
            // Le nom d'utilisateur existe déjà, vous pouvez gérer cela ici, par exemple, en renvoyant un message d'erreur
            return "redirect:/inscription";
        } else {
       
 
    	
    	
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(username);
        utilisateur.setPassword(password);
        utilisateurRepository.save(utilisateur);
    	
        return "redirect:/connexion";
    }
    }
	
    }
