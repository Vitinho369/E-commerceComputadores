package eaj.ufrn.br.ecommercecomputadores.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="computador")
public class Computador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "O nome do produto não pode ser vazio")
    private String nomeProduto;

    @DecimalMin("1")
    private float preco;

    @NotBlank(message = "A categoria não pode ser vazia")
    private String strategy;
}
