package eaj.ufrn.br.ecommercecomputadores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/cadastroUsuario")
    public String getCadusuario(){
        return "CadastroUsuario";
    }
}
