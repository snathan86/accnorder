package ebi.sd.accnorder.controller;

import ebi.sd.accnorder.model.AccessionNumbers;
import ebi.sd.accnorder.service.AccnGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Created by senthil on 10/7/17.
 */
@Controller
public class AccnGroupController {

    @Autowired
    AccnGroupService accnGroupService;

    @GetMapping("/accession")
    public String accessionForm(Model model) {
        model.addAttribute("accessionNumbers", new AccessionNumbers());

        return "home";
    }

    @PostMapping("/accession")
    public String groupSubmit(@ModelAttribute AccessionNumbers accessionNumbers, Model model) {
        model.addAttribute("accn", accnGroupService.groupAccnNumbers(accessionNumbers.getContent()));
        return "result";
    }


}
