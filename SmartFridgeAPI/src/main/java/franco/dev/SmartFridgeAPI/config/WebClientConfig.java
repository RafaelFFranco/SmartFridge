package franco.dev.SmartFridgeAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${GEMINI_URL}")
    private String chatGptApiUrl;

    // cria um bean para gerenciamento da requisição ao chatGpt, o contexto do webflux
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(chatGptApiUrl).build();
    }
}
