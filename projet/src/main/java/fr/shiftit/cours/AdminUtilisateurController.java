package fr.shiftit.cours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Controller
@RequestMapping("/Administrateur")
public class AdminUtilisateurController {
	
	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	
	
	
	@GetMapping(path = "/Utilisateurs")
	public String pageAdminUtilisateur(Model model, HttpSession session) {
		
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
			    List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
			    model.addAttribute("utilisateurs", utilisateurs);  
				
				return "AdminUtilisateurs";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


        
    }
	
	
	@PostMapping("/ajouter-utilisateur")
	public String ajouterUtilisateur(HttpSession session, Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("admin") String admin) {
	   

			if(session.getAttribute("user") != null) {
				//On vérifie si l'attibut user est non null
				
				Utilisateur user = (Utilisateur) session.getAttribute("user");
				
				if(user.getAdmin()) {
					
					Utilisateur dejaPris = utilisateurRepository.findByUsername(username).orElse(null);
					
					if(dejaPris==null) {
						
						Utilisateur utilisateur = new Utilisateur();
						
						utilisateur.setUsername(username);
						utilisateur.setPassword(password);	
						utilisateur.setAdmin(Boolean.parseBoolean(admin));
						utilisateurRepository.save(utilisateur);
						
						
					    return "redirect:/Administrateur/Utilisateurs";
						
						
						
					}else {
					
				    List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
					model.addAttribute("ErreurAjout","Erreur, le nom d'utilisateur est déjà pris");
				    model.addAttribute("utilisateurs", utilisateurs);  
					
					return "AdminUtilisateurs";
					}
					
				}else {
					
					return "redirect:/index";
					
				}
			}

	        
			return "redirect:/connexion";
			
		
			
		}
			
			
			
		
	

	
    @Transactional
	@PostMapping("/supprimer-utilisateur")
	public String supprimerProduit(HttpSession session,@RequestParam Long utilisateurId) {
			
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
				utilisateurRepository.deleteById(utilisateurId);
			    return "redirect:/Administrateur/Utilisateurs";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


	}
    
    
    @GetMapping("/modifier-utilisateur/{utilisateurId}")
    public String afficherFormulaireModification(@PathVariable Long utilisateurId, Model model, HttpSession session) {
    	
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
		        if (utilisateur != null) {
		            model.addAttribute("utilisateur", utilisateur);
		            return "ModifierUtilisateur";
		        } else {
		            // Gérer le cas où le produit n'a pas été trouvé
		    	    return "redirect:/Administrateur/Utilisateurs";
		        }

			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";
		
    }

    @PostMapping("/modifier-utilisateur")
    public String modifierUtilisateur(HttpSession session,Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
    		@RequestParam("id") Long id,
    		@RequestParam("admin") String admin) {
    	
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
		    	Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
		    	
		    	if(!(utilisateur.getUsername().equals(username))) {
		    		
		    	
		    		Utilisateur dejaPris = utilisateurRepository.findByUsername(username).orElse(null);
		    		
		    		if(dejaPris!=null) {
		    			
		    			model.addAttribute("ErreurModif","Erreur nom d'utilisateur déjà pris");
		    			model.addAttribute("utilisateur",utilisateur);
		    			return "ModifierUtilisateur";
		    			
		    		}
		    		
		    	}
		    	
		    	utilisateur.setUsername(username);
		    	utilisateur.setPassword(password);
		    	utilisateur.setAdmin(Boolean.parseBoolean(admin));
		    	
		    	utilisateurRepository.save(utilisateur);
		    	
		        return "redirect:/Administrateur/Utilisateurs";
				
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";
    	
        

    }

}
