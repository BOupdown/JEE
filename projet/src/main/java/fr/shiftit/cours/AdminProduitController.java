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
public class AdminProduitController {
	
	@Autowired
    private ProduitRepository produitRepository;
	
	
	
	
	@GetMapping(path = "/Produits")
	public String pageAdminProduit(Model model, HttpSession session) {
		
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
			    List<Produit> produits = produitRepository.findAll();
			    model.addAttribute("produits", produits);  
				
				return "AdminProduits";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


        
    }
	
	
	@PostMapping("/ajouter-produit")
	public String ajouterProduit(HttpSession session, Model model,@RequestParam("nom") String nom,
			@RequestParam("description") String description,
			@RequestParam("prix") Float prix,
			@RequestParam("photo") String photo,
			@RequestParam("stock") Long stock) {
	   

			if(session.getAttribute("user") != null) {
				//On vérifie si l'attibut user est non null
				
				Utilisateur user = (Utilisateur) session.getAttribute("user");
				
				if(user.getAdmin()) {
					
					Produit dejaPris = produitRepository.findByNom(nom).orElse(null);
					
					if(dejaPris==null) {
						
						Produit produit = new Produit();
						
						produit.setNom(nom);
						produit.setDescription(description);
						produit.setPrix(prix);
						produit.setPhoto(photo);
						produit.setStock(stock);
						
						produitRepository.save(produit);
						
						
					    return "redirect:/Administrateur/Produits";
						
						
						
					}else {
					
				    List<Produit> produits = produitRepository.findAll();
					model.addAttribute("ErreurAjout","Erreur, le nom de ce produit est déjà pris");
				    model.addAttribute("produits", produits);  
					
					return "AdminProduits";
					}
					
				}else {
					
					return "redirect:/index";
					
				}
			}

	        
			return "redirect:/connexion";
			
		
			
		}
			
			
			
		
	

	
    @Transactional
	@PostMapping("/supprimer-produit")
	public String supprimerProduit(HttpSession session,@RequestParam Long produitId) {
			
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
				produitRepository.deleteById(produitId);
			    return "redirect:/Administrateur/Produits";
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";


	}
    
    
    @GetMapping("/modifier-produit/{produitId}")
    public String afficherFormulaireModification(@PathVariable Long produitId, Model model, HttpSession session) {
    	
		if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				Produit produit = produitRepository.findById(produitId).orElse(null);
		        if (produit != null) {
		            model.addAttribute("produit", produit);
		            return "ModifierProduit";
		        } else {
		            // Gérer le cas où le produit n'a pas été trouvé
		    	    return "redirect:/Administrateur/Produits";
		        }

			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";
		
    }

    @PostMapping("/modifier-produit")
    public String modifierProduit(HttpSession session,Model model,@RequestParam("nom") String nom,
			@RequestParam("description") String description,
			@RequestParam("prix") Float prix,
			@RequestParam("photo") String photo,
			@RequestParam("stock") Long stock,
			@RequestParam("id") Long id ) {
    	
    	if(session.getAttribute("user") != null) {
			//On vérifie si l'attibut user est non null
			
			Utilisateur user = (Utilisateur) session.getAttribute("user");
			
			if(user.getAdmin()) {
				
		    	Produit produit = produitRepository.findById(id).orElse(null);
		    	
		    	if(!(produit.getNom().equals(nom))) {
		    		
		    	
		    		Produit dejaPris = produitRepository.findByNom(nom).orElse(null);
		    		
		    		if(dejaPris!=null) {
		    			
		    			model.addAttribute("ErreurModif","Erreur nom de produit déjà pris");
		    			model.addAttribute("produit",produit);
		    			return "ModifierProduit";
		    			
		    		}
		    		
		    	}
		    	
		    	produit.setNom(nom);
		    	produit.setDescription(description);
		    	produit.setPrix(prix);
		    	produit.setPhoto(photo);
		    	produit.setStock(stock);
		    	
		    	produitRepository.save(produit);
		    	
		        return "redirect:/Administrateur/Produits";
				
			}else {
				
				return "redirect:/index";
				
			}
		}

        
		return "redirect:/connexion";
    	
        

    }

}
