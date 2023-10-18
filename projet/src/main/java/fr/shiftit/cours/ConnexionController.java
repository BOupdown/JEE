package fr.shiftit.cours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DptController {

	@Autowired
	DptRepository dptRepository;
	
	@GetMapping(path = "/ioupi")
	public String ioupi(Model model) {
		
		Department dpt = new Department();
//		dpt.setName("cytech");
//		dptRepository.save(dpt);
		dpt = dptRepository.findByName("cytech").get();
		
		model.addAttribute("dpt", dpt);
		return "index";
	}
}
