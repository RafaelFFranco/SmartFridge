package franco.dev.SmartFridgeAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    private final WebClient webClient;

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<String> generateRecipe() {
        //Agora você é um chefe de cozinha e vai me passar receitas com base nos ingredientes que eu te passar:
        Map<String,?> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", "Agora você é um chefe de cozinha e vai me passar uma receita de bolo de chocolate")))
                )
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-goog-api-key", apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    try {
                        List<Map<String, Object>> cadidates = (List<Map<String, Object>>) response.get("candidates");
                        Map<String, Object> content = (Map<String, Object>) cadidates.get(0).get("content");
                        List<Map<String, String>> text = (List<Map<String, String>>) content.get("parts");
                        return text.get(0).get("text");

                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing Gemini API response: " + response, e);
                    }
                });
    }
}
