package eaj.ufrn.br.ecommercecomputadores.service;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import eaj.ufrn.br.ecommercecomputadores.repository.ComputadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComputadorService {

    private final ComputadorRepository repository;

    public ComputadorService(ComputadorRepository repository){
        this.repository = repository;
    }

    public Optional<Computador> findById(Long id){
        return  repository.findById(id);
    }
    public Computador update(Computador p){
        return repository.saveAndFlush(p);
    }
    public Computador create(Computador p){
        return repository.save(p);

    }
    public List<Computador> findAll(){
        return repository.findAll();
    }
}