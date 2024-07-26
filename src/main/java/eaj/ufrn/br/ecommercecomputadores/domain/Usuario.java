package eaj.ufrn.br.ecommercecomputadores.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "O nome de usuário não pode ser vazio")
    private String username;

    @NotBlank(message = "O nome de usuário não pode ser vazio")
    private String password;

    @NotNull(message = "O usuário deve ser um admin ou um usuário comum")
    private boolean isAdmin;
}
