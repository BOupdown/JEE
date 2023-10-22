package fr.shiftit.cours;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class PageProduitController {
	private static final Logger logger = Logger.getLogger(PageProduitController.class.getName());

	
	@Autowired
    private ProduitRepository produitRepository;

	
	@GetMapping(path = "/pageProduit")
	public String pageProduit(Model model, HttpSession session) {
    	List<Produit> produits = produitRepository.findAll();
    	
    	for (Produit produit : produits) {
            logger.info("Product Name: " + produit.getNom());
        }

    	
    	session.setAttribute("produits", produits);
    	

        return "pageProduit";
	
	}

    

}
