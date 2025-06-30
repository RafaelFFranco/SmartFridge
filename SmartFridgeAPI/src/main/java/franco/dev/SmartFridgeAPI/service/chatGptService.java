package franco.dev.SmartFridgeAPI.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@Service
public class chatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("OPENAI_API_KEY");

    public chatGptService(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<String> generateRecipe() {
        //Agora você é um chefe de cozinha e vai me passar receitas com base nos ingredientes que eu te passar:
        Map<String, String> systemRole = Map.of(
                "role","system",
                "content","seja um guia"
        );
        Map<String, String> userRole = Map.of(
                "role","user",
                "content","ola!"
        );
        List<Map<String, String>> messages = List.of(systemRole, userRole);

        Map<String,Object> requestBody = Map.of(
                "model","gpt-4.1",
                "messages",messages
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    if (!response.isEmpty() && response.containsKey("content")) {
                        List<?> contentResponse = (List<?>) response.get("content");
                        if (!contentResponse.isEmpty()) {
                            Map<String, Object> recipeResponse = (Map<String, Object>) contentResponse.get(0);
                            String recipe = (String) recipeResponse.get("text");
                            return recipe;
                        }
                    }
                    return "Erro ao gerar a receita.";
                });
    }
}
