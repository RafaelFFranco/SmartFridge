package franco.dev.SmartFridgeAPI.model;

import franco.dev.SmartFridgeAPI.model.ENUM.itemCategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "item_comida")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private itemCategoria categoria;
    private int quantidade;
    private LocalDate validade;

}
