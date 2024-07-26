package eaj.ufrn.br.ecommercecomputadores.repository;

import eaj.ufrn.br.ecommercecomputadores.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
