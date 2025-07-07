package franco.dev.SmartFridgeAPI.repository;

import franco.dev.SmartFridgeAPI.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
