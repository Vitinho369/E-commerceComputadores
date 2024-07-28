package eaj.ufrn.br.ecommercecomputadores.repository;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);


}
