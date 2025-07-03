package franco.dev.SmartFridgeAPI.controller;

import franco.dev.SmartFridgeAPI.model.ItemComida;
import franco.dev.SmartFridgeAPI.service.ItemComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemComidaController {

    @Autowired
    private ItemComidaService service;

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody ItemComida itemComida) {
        service.add(itemComida);
        return ResponseEntity.ok("Produto criado com sucesso!");
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<ItemComida> itemComida = service.findById(id);
        if (itemComida.isPresent()) {
            return ResponseEntity.ok(itemComida.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ItemComida> itemComida = service.findById(id);
        if (itemComida.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@RequestBody ItemComida itemComida, @PathVariable Long id) {
        service.update(itemComida,id);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }
}
