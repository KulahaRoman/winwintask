package winwin.auth_api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {
    @Value("${spring.security.token.service.secret}")
    private String serviceToken;

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl("http://data-api:8081/api/transform")
                .defaultHeader("X-Internal-Token", serviceToken)
                .build();
    }
}
