package fr.shiftit.cours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class PageProduitController {

	
	@Autowired
    private ProduitRepository produitRepository;

	
	@GetMapping(path = "/pageProduit")
	public String pageProduit(Model model, HttpSession session) {
		//liste de produits
	    List<Produit> produits = produitRepository.findAll();
	    model.addAttribute("produits", produits);  
	    //retourne page produit
	    return "pageProduit";
	}

}

    

