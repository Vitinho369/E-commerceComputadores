package eaj.ufrn.br.ecommercecomputadores.controller;

import eaj.ufrn.br.ecommercecomputadores.domain.Carrinho;
import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import eaj.ufrn.br.ecommercecomputadores.service.ComputadorService;
import eaj.ufrn.br.ecommercecomputadores.service.FileStorageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;


@Controller
public class LojaController {

    private final ComputadorService service;

    public LojaController(ComputadorService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String getIndex(Model model, HttpServletResponse response){
        model.addAttribute("computadores", service.findNotDeleted());
        LocalDateTime dataMomento = LocalDateTime.now();

        Cookie  cookie = new Cookie("visita", dataMomento.toString());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "index";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(HttpServletRequest resquest, @PathVariable Long id){
        HttpSession sessao = resquest.getSession(false);

        ModelAndView modelAndView = new ModelAndView("redirect:/index");
//        modelAndView.addObject("computadores", service.findNotDeleted());
        Carrinho carrinho = (Carrinho) sessao.getAttribute("Carrinho");

        Optional<Computador> computadorCar = service.findById(id);

        if(computadorCar.isPresent()) carrinho.addComputador(computadorCar.get());
        modelAndView.addObject("carrinho", carrinho);

        return modelAndView;
    }

}
