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

    public itemComida findById(Long id) {
        Optional<itemComida> itemComida = repository.findById(id);
        return itemComida.orElse(null);
    }

    public void delete(Long id){
        Optional<itemComida> itemComida = repository.findById(id);
        if (itemComida.isPresent()) {
            repository.deleteById(id);
        }
    }

    public itemComida update(itemComida itemComida) {
        Optional<itemComida> optional = repository.findById(itemComida.getId());

        if (optional.isPresent()) {
            itemComida itemComidaAntigo = optional.get();

            itemComidaAntigo.setNome(itemComida.getNome());
            itemComidaAntigo.setCategoria(itemComida.getCategoria());
            itemComidaAntigo.setQuantidade(itemComida.getQuantidade());
            itemComidaAntigo.setValidade(itemComida.getValidade());

            return repository.save(itemComidaAntigo);
        }else {
            throw new RuntimeException("Item não encontrado");
        }
    }



}
