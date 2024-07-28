package eaj.ufrn.br.ecommercecomputadores.service;

import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import eaj.ufrn.br.ecommercecomputadores.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }
    public Optional<Usuario> findById(Long id){
        return  repository.findById(id);
    }
    public Usuario update(Usuario u){
        return repository.saveAndFlush(u);
    }
    public Usuario create(Usuario u){
        return repository.save(u);

    }


}
