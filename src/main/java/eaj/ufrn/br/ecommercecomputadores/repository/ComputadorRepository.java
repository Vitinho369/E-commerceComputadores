package eaj.ufrn.br.ecommercecomputadores.repository;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputadorRepository extends JpaRepository<Computador, Long> {
}
