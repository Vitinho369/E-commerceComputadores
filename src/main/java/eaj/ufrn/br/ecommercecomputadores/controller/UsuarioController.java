package eaj.ufrn.br.ecommercecomputadores.controller;

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
    public ModelAndView setCadastro(@ModelAttribute @Valid Usuario u, Errors errors, @RequestParam("isEdit") boolean isEdit , @RequestParam("isAdmin") boolean isAdmin) throws IOException {

        if(isEdit) { //Verifica se veio da pagina de edição ou de cadastro

            Optional<Usuario> usuario = service.findById(u.getId());
            if (usuario.isPresent()) {

                if(errors.hasErrors()){
                    ModelAndView modelAndView = new ModelAndView("/editar");
                    return modelAndView;
                }


                service.update(u);
                ModelAndView mv = new ModelAndView("redirect:/index");
                mv.addObject("msg", "Atualização realizada com sucesso");
                return mv;
            }
        }

        if(errors.hasErrors()){
            return new ModelAndView("redirect:/cadastroUsuario");
        }
        u.setAdmin(isAdmin);
        service.create(u);
//        System.out.println("O usuário é admin? " + u.getIsAdmin());
        System.out.println("Cadastrou");
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        return modelAndView;

    }


}
