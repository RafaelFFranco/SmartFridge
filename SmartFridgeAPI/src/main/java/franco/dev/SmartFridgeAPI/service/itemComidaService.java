package franco.dev.SmartFridgeAPI.service;

import franco.dev.SmartFridgeAPI.model.itemComida;
import franco.dev.SmartFridgeAPI.repository.itemComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class itemComidaService {

    @Autowired
    private itemComidaRepository repository;


    public List<itemComida> findAll() {
        return repository.findAll();
    }

    public

}
