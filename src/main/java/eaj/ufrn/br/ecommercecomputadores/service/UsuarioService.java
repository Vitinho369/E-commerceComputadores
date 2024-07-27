package eaj.ufrn.br.ecommercecomputadores.service;

import eaj.ufrn.br.ecommercecomputadores.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }
}
