package franco.dev.SmartFridgeAPI.service;

import franco.dev.SmartFridgeAPI.model.Receita;
import franco.dev.SmartFridgeAPI.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    private ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }


    public Receita add(Receita receita) {
        try {
            return receitaRepository.save(receita);
        }catch (Exception e){
            throw new RuntimeException("Erro ao adicionar receita" + e.getMessage());
        }
    }

    public List<Receita> getAll() {
      return receitaRepository.findAll();
    }

    public void delete(Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if(!receita.isPresent()) {
            throw new RuntimeException("Erro ao excluir receita: receita inexistente");
        }
        try{
            receitaRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao excluir receita" + e.getMessage());
        }
    }
}
