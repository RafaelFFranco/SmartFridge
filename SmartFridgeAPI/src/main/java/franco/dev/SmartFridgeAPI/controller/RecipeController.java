package franco.dev.SmartFridgeAPI.controller;

import franco.dev.SmartFridgeAPI.model.Receita;
import franco.dev.SmartFridgeAPI.service.ChatGptService;
import franco.dev.SmartFridgeAPI.service.ItemComidaService;
import franco.dev.SmartFridgeAPI.service.ReceitaService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/recipe")
public class RecipeController {


    private ChatGptService chatGptService;
    private ItemComidaService itemComidaService;
    private ReceitaService receitaService;

    public RecipeController(ChatGptService chatGptService, ItemComidaService itemComidaService, ReceitaService receitaService) {
        this.chatGptService = chatGptService;
        this.itemComidaService = itemComidaService;
        this.receitaService = receitaService;
    }

    //Get anotation porque nós queremos recuperar os dados de uma requisição enviada para a API do chat gpt
    @GetMapping("/generate")
    //Mono é um interface do WebFlux para lidar com requisições assíncronas, coisas são feitas ao mesmo tempo mas existe um tempo de espera
    //ele vai me devolver uma resposta mas vai demorar um pouco
    //ResponseEntity é feito com String porque o retorno do prompt é uma String
    public Mono<ResponseEntity<String>> generateRecipe(){
        return chatGptService.generateRecipe(itemComidaService.getAll())
                .map(recipe -> ResponseEntity.ok().body(recipe))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/add")
    public ResponseEntity<?> saveRecipe(@RequestBody Receita receita){
        try{
            receitaService.add(receita);
            return ResponseEntity.ok().body(receita);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/${id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id){
        try {
            receitaService.delete(id);
            return ResponseEntity.ok("Item deletado com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes(){
        try{
            return ResponseEntity.ok().body(receitaService.getAll());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
