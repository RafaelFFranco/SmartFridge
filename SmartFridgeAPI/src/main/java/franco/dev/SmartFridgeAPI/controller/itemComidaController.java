package franco.dev.SmartFridgeAPI.controller;

import franco.dev.SmartFridgeAPI.model.itemComida;
import franco.dev.SmartFridgeAPI.service.itemComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class itemComidaController {

    @Autowired
    private itemComidaService service;

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody itemComida itemComida) {
        service.add(itemComida);
        return ResponseEntity.ok("Produto criado com sucesso!");
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/get{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<itemComida> itemComida = service.findById(id);
        if (itemComida.isPresent()) {
            return ResponseEntity.ok(itemComida.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<itemComida> itemComida = service.findById(id);
        if (itemComida.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@RequestBody itemComida itemComida,@PathVariable Long id) {
        service.update(itemComida,id);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }
}
