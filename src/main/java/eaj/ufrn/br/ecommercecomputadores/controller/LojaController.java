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
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
public class LojaController {

    private final ComputadorService service;

    public LojaController(ComputadorService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String getIndex(Model model, HttpServletResponse response, HttpServletRequest request) {
        model.addAttribute("computadores", service.findNotDeleted());
        LocalDateTime dataMomento = LocalDateTime.now();
        HttpSession session = request.getSession(false);

        if(session==null){
            System.out.println("criei nova sessão");
            session = request.getSession();
        }else{
            System.out.println("não criei nova sessão");
        }

        Carrinho car = (Carrinho) session.getAttribute("Carrinho");
        if(car == null) {
            System.out.println("carrinho é nulo");
            car = new Carrinho();
        }
        session.setAttribute("Carrinho", car);

        Cookie cookie = new Cookie("visita", dataMomento.toString());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "index";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(HttpServletRequest resquest, @PathVariable Long id) {
        HttpSession sessao = resquest.getSession(false);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("computadores", service.findNotDeleted());
        Carrinho carrinho = (Carrinho) sessao.getAttribute("Carrinho");

        Optional<Computador> computadorCar = service.findById(id);

        if (computadorCar.isPresent()) carrinho.addComputador(computadorCar.get());
        sessao.setAttribute("Carrinho", carrinho);
        modelAndView.addObject("carrinho", carrinho);

        return modelAndView;
    }

    @GetMapping("/verCarrinho")
    public ModelAndView getCarrinho(Model model, HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);
        Carrinho carrinho = (Carrinho) sessao.getAttribute("Carrinho");
        if (carrinho.getComputadores().isEmpty()) {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("msg", "Não existem itens no carrinho");
            mv.addObject("computadores",service.findNotDeleted());
            return mv;
        }

        ModelAndView mv = new ModelAndView("verCarrinho");
        mv.addObject("carrinho",carrinho);
        return mv  ;
    }

    @GetMapping("/removerItemCarrinho/{id}")
    public ModelAndView removerItemCarrinho(@PathVariable String id, HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        Carrinho carrinho = (Carrinho) sessao.getAttribute("Carrinho");
        carrinho.removeComputador(Long.parseLong(id));

        sessao.setAttribute("Carrinho", carrinho);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("computadores", service.findNotDeleted());

        return modelAndView;
    }
    @GetMapping("/finalizarCompra")
    public ModelAndView finalizarCompra(HttpServletRequest resquest){
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        modelAndView.addObject("computadores", service.findNotDeleted());
        HttpSession sessao =  resquest.getSession(false);

        Carrinho carrinho = (Carrinho) sessao.getAttribute("Carrinho");
        List<Computador> computadorList = carrinho.getComputadores();

        for(Computador c : computadorList){
            Optional<Computador> compBanco = service.findById(c.getId());

            if(compBanco.isPresent()){
                int qtd = compBanco.get().getQtd();

                if(qtd - c.getQtd() < 0){
                    modelAndView.addObject("msg", "Não é possível comprar nesta quantidade");
                    break;
                }

                modelAndView.addObject("msg", "Compra realizada com sucesso");
                if(qtd - c.getQtd() == 0) {
                    compBanco.get().setQtd(0);
                    service.update(compBanco.get());
                    service.delete(c);
                }else{
                    compBanco.get().setQtd(qtd - c.getQtd());
                    service.update(compBanco.get());
                }
            }
        }
        sessao.invalidate();
        return modelAndView;
    }
}