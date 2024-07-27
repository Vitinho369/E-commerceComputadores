package eaj.ufrn.br.ecommercecomputadores.repository;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComputadorRepository extends JpaRepository<Computador, Long> {

    @Query("SELECT c FROM computador c WHERE c.isDeleted IS NULL")
    List<Computador> findNotDeleted();
}
