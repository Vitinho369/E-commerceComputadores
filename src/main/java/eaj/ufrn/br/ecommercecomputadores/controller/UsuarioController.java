package eaj.ufrn.br.ecommercecomputadores.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import eaj.ufrn.br.ecommercecomputadores.service.ComputadorService;
import eaj.ufrn.br.ecommercecomputadores.service.FileStorageService;
import eaj.ufrn.br.ecommercecomputadores.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;



@Controller
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService service){
        this.service = service;
    }
    @GetMapping("/cadastroUsuario")
    public String getCadusuario(Model model)   {
        Usuario u = new Usuario();
        model.addAttribute("usuario",u);
        return "CadastroUsuario";
    }

    @PostMapping("/CadUsuario")
    public ModelAndView setCadastro(@ModelAttribute @Valid Usuario u, Errors errors, @RequestParam("isEdit") boolean isEdit ) throws IOException {

        if(errors.hasErrors()){
            return new ModelAndView("redirect:/cadastroUsuario");
        }

        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));

//        u.setAdmin(isAdmin);
        service.create(u);

        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        return modelAndView;

    }


}
