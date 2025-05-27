package franco.dev.SmartFridgeAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class chatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("API_KEY");

    public chatGptService(WebClient webClient) {
        this.webClient = webClient;
    }


    /*
    curl "https://api.openai.com/v1/responses" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $OPENAI_API_KEY" \
            -d '{
            "model": "gpt-4.1",
            "input": "Write a one-sentence bedtime story about a unicorn."
}'
*/

    public Mono<String> generateRecipe(){
        String prompt = "Agora você é um chefe de cozinha e vai me passar receitas com base nos ingredientes que eu te passar.";

        Map<String,Object> requestBody = Map.of(
                "model","gpt-4.1",
                "input",prompt
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.AUTHORIZATION,"Bearer" + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }
}
