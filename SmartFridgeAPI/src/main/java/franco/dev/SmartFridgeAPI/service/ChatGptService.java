package franco.dev.SmartFridgeAPI.service;

import franco.dev.SmartFridgeAPI.model.ItemComida;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    private final WebClient webClient;

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<String> generateRecipe(List<ItemComida> foodItems) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String foodData = foodItems.stream()
                .map(food -> String.format("Nome: %s - Quantidade: %d - Validade: %s",
                        food.getNome(),food.getQuantidade(),food.getValidade().format(formatter)))
                .collect(Collectors.joining("\n")
                );

        Map<String,?> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", "Gere uma receita de uma refeição que eu possa fazer com base no meu banco de dados: \n" + foodData )))
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
