package eaj.ufrn.br.ecommercecomputadores.controller;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import eaj.ufrn.br.ecommercecomputadores.service.ComputadorService;
import eaj.ufrn.br.ecommercecomputadores.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Controller
public class AdminController {
    private final ComputadorService service;
    private final FileStorageService fileStorageService;

    public AdminController(ComputadorService service, FileStorageService fileStorageService){
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/admin")
    public String ListAll(Model model){
        model.addAttribute("computadores", service.findNotDeleted());
        return "listagem";
    }
    @GetMapping("/cadastro")
    public String getCadastro(Model model){
        Computador c = new Computador();
        model.addAttribute("computador",c);
        return  "cadastro";
    }

    @PostMapping("/salvar")
    public ModelAndView setCadastro(@ModelAttribute @Valid Computador c, Errors errors, @RequestParam("file") MultipartFile file, @RequestParam("isEdit") boolean isEdit) throws IOException {

        if(isEdit) { //Verifica se veio da pagina de edição ou de cadastro

            Optional<Computador> computador = service.findById(c.getId());
            if (computador.isPresent()) {

                if(errors.hasErrors()){
                    ModelAndView modelAndView = new ModelAndView("/editar");
                    return modelAndView;
                }

                c.setImageUri("/images/" + fileStorageService.update(file, computador.get()));//Atualiza a imagem do computador sabendo o nome origina dela
                service.update(c);
                ModelAndView mv = new ModelAndView("/listagem");
                mv.addObject("msg", "Atualização realizada com sucesso");
                mv.addObject("computadores", service.findNotDeleted());
                return mv;
            }
        }

        if(errors.hasErrors()){
            return new ModelAndView("cadastro");
        }

        c.setImageUri("/images/"+fileStorageService.save(file)); //Para evitar imagens com mesmo nome, o fileStorageService altera o nome da imagem e retorna o nome alterado
        service.create(c);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        modelAndView.addObject("computadores", service.findNotDeleted());
        return modelAndView;

    }

    @GetMapping("/editar/{id}")
    public ModelAndView getEditPage(@PathVariable Long id){

        Optional<Computador> computador = service.findById(id);
        if (computador.isPresent()){
            ModelAndView mv = new ModelAndView("editar");

            mv.addObject("msg", "atualização realizada com sucesso");
            mv.addObject("computador", computador.get());
            return mv;
        }else{
            return new ModelAndView("redirect:/index");
        }
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView delete(@PathVariable Long id){

        Optional<Computador> computador = service.findById(id);
        if (computador.isPresent()){

            service.delete(computador.get());
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("computadores", service.findNotDeleted());
            mv.addObject("msg", "Remoção realizada com sucesso");
            return mv;
        }else{
            return new ModelAndView("redirect:/index");
        }
    }

}
