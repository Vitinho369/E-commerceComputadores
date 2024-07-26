package eaj.ufrn.br.ecommercecomputadores.controller;

import eaj.ufrn.br.ecommercecomputadores.service.ComputadorService;
import eaj.ufrn.br.ecommercecomputadores.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LojaController {

    private final ComputadorService service;
    private final FileStorageService fileStorageService;

    public LojaController(ComputadorService service, FileStorageService fileStorageService){
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/index")
    public String getIndex(Model model){
            model.addAttribute("computadores", service.findAll());
        return "index";
    }

}
