package franco.dev.SmartFridgeAPI.service;

import franco.dev.SmartFridgeAPI.model.ItemComida;
import franco.dev.SmartFridgeAPI.repository.ItemComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemComidaService {

    @Autowired
    private ItemComidaRepository repository;


    public List<ItemComida> getAll() {
        return repository.findAll();
    }

    public void add(ItemComida itemComida) {
        repository.save(itemComida);
    }

    public Optional<ItemComida> findById(Long id) {
        Optional<ItemComida> itemComida = repository.findById(id);
        return itemComida;
    }

    public void delete(Long id){
        Optional<ItemComida> itemComida = repository.findById(id);
        if (itemComida.isPresent()) {
            repository.deleteById(id);
        }
    }

    public ItemComida update(ItemComida itemComida, Long id) {
        Optional<ItemComida> optional = repository.findById(id);

        if (optional.isPresent()) {
            ItemComida itemComidaNovo = optional.get();

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