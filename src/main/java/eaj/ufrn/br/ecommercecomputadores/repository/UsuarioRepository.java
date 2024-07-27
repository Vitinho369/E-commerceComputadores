package eaj.ufrn.br.ecommercecomputadores.repository;

import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
