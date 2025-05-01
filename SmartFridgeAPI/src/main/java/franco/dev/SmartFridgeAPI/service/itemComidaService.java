package franco.dev.SmartFridgeAPI.service;

import franco.dev.SmartFridgeAPI.model.itemComida;
import franco.dev.SmartFridgeAPI.repository.itemComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class itemComidaService {

    @Autowired
    private itemComidaRepository repository;


    public List<itemComida> findAll() {
        return repository.findAll();
    }

    public void add(itemComida itemComida) {
        repository.save(itemComida);
    }

    public Optional<itemComida> findById(Long id) {
        Optional<itemComida> itemComida = repository.findById(id);
        return itemComida;
    }

    public void delete(Long id){
        Optional<itemComida> itemComida = repository.findById(id);
        if (itemComida.isPresent()) {
            repository.deleteById(id);
        }
    }

    public itemComida update(itemComida itemComida, Long id) {
        Optional<itemComida> optional = repository.findById(id);

        if (optional.isPresent()) {
            itemComida itemComidaNovo = optional.get();

            itemComidaNovo.setId(id);
            itemComidaNovo.setNome(itemComida.getNome());
            itemComidaNovo.setCategoria(itemComida.getCategoria());
            itemComidaNovo.setQuantidade(itemComida.getQuantidade());
            itemComidaNovo.setValidade(itemComida.getValidade());

            return repository.save(itemComidaNovo);
        }else {
            throw new RuntimeException("Item não encontrado");
        }
    }
}