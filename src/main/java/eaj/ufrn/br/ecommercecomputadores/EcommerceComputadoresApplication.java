package eaj.ufrn.br.ecommercecomputadores;

import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import eaj.ufrn.br.ecommercecomputadores.repository.ComputadorRepository;
import eaj.ufrn.br.ecommercecomputadores.service.ComputadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.script.Compilable;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class EcommerceComputadoresApplication  implements WebMvcConfigurer{

    @Autowired
    static ComputadorService service;
    public static void main(String[] args) {
        SpringApplication.run(EcommerceComputadoresApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register resource handler for images
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		/*
		registry.addResourceHandler("/images/**").addResourceLocations("/images/")
		.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());*/
    }

}