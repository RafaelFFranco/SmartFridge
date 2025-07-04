package franco.dev.SmartFridgeAPI.controller;

import franco.dev.SmartFridgeAPI.model.ItemComida;
import franco.dev.SmartFridgeAPI.service.ChatGptService;
import franco.dev.SmartFridgeAPI.service.ItemComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private ChatGptService chatGptService;
    @Autowired
    private ItemComidaService itemComidaService;

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

}
